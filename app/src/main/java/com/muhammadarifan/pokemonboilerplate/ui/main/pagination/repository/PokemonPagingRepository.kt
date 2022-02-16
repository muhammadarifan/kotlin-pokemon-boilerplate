package com.muhammadarifan.pokemonboilerplate.ui.main.pagination.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.muhammadarifan.pokemonboilerplate.local.model.Pokemon
import com.muhammadarifan.pokemonboilerplate.local.repository.PokemonRepository
import com.muhammadarifan.pokemonboilerplate.ui.main.pagination.remote_mediator.PokemonRemoteMediator
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ExperimentalPagingApi
class PokemonPagingRepository @Inject constructor(
    private val localPokemonRepository: PokemonRepository,
    private val pokemonRemoteMediator: PokemonRemoteMediator
){
    fun getPokemon() : Flow<PagingData<Pokemon>> {
        val pagingSourceFactory = { localPokemonRepository.getPokemon() }
        return Pager(
            config = PagingConfig(pageSize = 20),
            remoteMediator = pokemonRemoteMediator,
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }
}