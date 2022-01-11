package com.muhammadarifan.klikdigitaltask.pagination.remote_mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.muhammadarifan.klikdigitaltask.local.PokemonDatabase
import com.muhammadarifan.klikdigitaltask.local.model.Pokemon
import com.muhammadarifan.klikdigitaltask.local.model.PokemonRemoteKey
import com.muhammadarifan.klikdigitaltask.remote.PokemonApi
import javax.inject.Inject

@ExperimentalPagingApi
class PokemonRemoteMediator @Inject constructor(
    private val pokemonApi: PokemonApi,
    private val pokemonDatabase: PokemonDatabase
) : RemoteMediator<Int, Pokemon>() {
    private val pokemonDao = pokemonDatabase.getPokemonDao()
    private val pokemonRemoteKeyDao = pokemonDatabase.getPokemonRemoteKeyDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Pokemon>
    ): MediatorResult {
        return try {
            val currentPage = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKeys?.next?.minus(1) ?: 1
                }
                LoadType.PREPEND -> {
                    val remoteKeys = getRemoteKeyForFirstItem(state)
                    val prevPage = remoteKeys?.previous
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    prevPage
                }
                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    val nextPage = remoteKeys?.next
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    nextPage
                }
            }

            val response = pokemonApi.getPokemon(offset = ((currentPage - 1) * 20), limit = 20)
            val endOfPaginationReached = response.results.isEmpty()

            val prevPage = if (currentPage == 1) null else currentPage - 1
            val nextPage = if (endOfPaginationReached) null else currentPage + 1

            pokemonDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    pokemonDao.deletePokemon()
                    pokemonRemoteKeyDao.deleteRemoteKey()
                }
                val keys = response.results.map { pokemon ->
                    PokemonRemoteKey(
                        id = pokemon!!.url.dropLast(1).takeLastWhile { it.isDigit() },
                        next = nextPage,
                        previous = prevPage
                    )
                }
                pokemonRemoteKeyDao.postAllRemoteKey(keys)

                response.results.map { pokemon ->
                    pokemonDao.postPokemon(Pokemon(
                        id = pokemon!!.url.dropLast(1).takeLastWhile { it.isDigit() }.toInt(),
                        name = pokemon.name,
                    ))
                }
            }
            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, Pokemon>
    ): PokemonRemoteKey? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                pokemonRemoteKeyDao.getRemoteKey(id = id.toString())
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, Pokemon>
    ): PokemonRemoteKey? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { pokemon ->
                pokemonRemoteKeyDao.getRemoteKey(id = pokemon.id.toString())
            }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, Pokemon>
    ): PokemonRemoteKey? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { pokemon ->
                pokemonRemoteKeyDao.getRemoteKey(id = pokemon.id.toString())
            }
    }
}