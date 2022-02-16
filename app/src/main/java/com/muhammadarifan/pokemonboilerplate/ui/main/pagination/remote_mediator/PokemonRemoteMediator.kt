package com.muhammadarifan.pokemonboilerplate.ui.main.pagination.remote_mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.muhammadarifan.pokemonboilerplate.local.model.Pokemon
import com.muhammadarifan.pokemonboilerplate.local.model.PokemonRemoteKey
import com.muhammadarifan.pokemonboilerplate.local.repository.PokemonDetailRepository
import com.muhammadarifan.pokemonboilerplate.local.repository.PokemonRemoteKeyRepository
import com.muhammadarifan.pokemonboilerplate.local.repository.PokemonTypeRepository
import com.muhammadarifan.pokemonboilerplate.remote.repository.PokemonRepository
import com.muhammadarifan.pokemonboilerplate.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import javax.inject.Inject
import com.muhammadarifan.pokemonboilerplate.local.repository.PokemonRepository as LocalPokemonRepository

@ExperimentalPagingApi
class PokemonRemoteMediator @Inject constructor(
    private val remotePokemonRepository: PokemonRepository,
    private val localPokemonRemoteKeyRepository: PokemonRemoteKeyRepository,
    private val localPokemonRepository: LocalPokemonRepository,
    private val localPokemonDetailRepository: PokemonDetailRepository,
    private val localPokemonTypeRepository: PokemonTypeRepository
) : RemoteMediator<Int, Pokemon>() {

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

            val pokemonResponse = remotePokemonRepository.getPokemon(offset = ((currentPage - 1) * 20), limit = 20)
            val endOfPaginationReached = pokemonResponse.data!!.results.isEmpty()

            val prevPage = if (currentPage == 1) null else currentPage - 1
            val nextPage = if (endOfPaginationReached) null else currentPage + 1

            if (loadType == LoadType.REFRESH) {
                localPokemonRepository.deletePokemon()
                localPokemonRemoteKeyRepository.deleteRemoteKey()
            }
            val keys = pokemonResponse.data.results.map { pokemon ->
                PokemonRemoteKey(
                    id = pokemon!!.url.dropLast(1).takeLastWhile { it.isDigit() },
                    next = nextPage,
                    previous = prevPage
                )
            }
            localPokemonRemoteKeyRepository.postRemoteKey(keys)

            pokemonResponse.data.results.map { pokemon ->
                coroutineScope {
                    val pokemonId = pokemon!!.url.dropLast(1).takeLastWhile { it.isDigit() }.toInt()
                    val detailResponseResource = withContext(Dispatchers.Default) {
                        remotePokemonRepository.getPokemonDetail(pokemonId)
                    }
                    val speciesResponseResource = withContext(Dispatchers.IO) {
                        remotePokemonRepository.getPokemonSpecies(pokemonId)
                    }

                    if (detailResponseResource is Resource.Success && speciesResponseResource is Resource.Success) {
                        val detailResponse = detailResponseResource.data!!
                        val speciesResponse = speciesResponseResource.data!!
                        val pokemonTypes = detailResponse.types.joinToString(",")

                        localPokemonRepository.postPokemon(Pokemon(
                            id = pokemon.url.dropLast(1).takeLastWhile { it.isDigit() }.toInt(),
                            name = pokemon.name,
                            color = speciesResponse.color.name,
                            types = pokemonTypes
                        ))
                    }
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
                localPokemonRemoteKeyRepository.getRemoteKey(id = id.toString())
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, Pokemon>
    ): PokemonRemoteKey? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { pokemon ->
                localPokemonRemoteKeyRepository.getRemoteKey(id = pokemon.id.toString())
            }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, Pokemon>
    ): PokemonRemoteKey? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { pokemon ->
                localPokemonRemoteKeyRepository.getRemoteKey(id = pokemon.id.toString())
            }
    }
}