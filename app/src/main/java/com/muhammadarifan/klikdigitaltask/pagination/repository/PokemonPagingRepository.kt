package com.muhammadarifan.klikdigitaltask.pagination.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.muhammadarifan.klikdigitaltask.local.PokemonDatabase
import com.muhammadarifan.klikdigitaltask.local.model.Pokemon
import com.muhammadarifan.klikdigitaltask.pagination.remote_mediator.PokemonRemoteMediator
import com.muhammadarifan.klikdigitaltask.remote.PokemonApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ExperimentalPagingApi
class PokemonPagingRepository @Inject constructor(
    private val pokemonApi: PokemonApi,
    private val pokemonDatabase: PokemonDatabase
){
    fun getPokemon() : Flow<PagingData<Pokemon>> {
        val pagingSourceFactory = { pokemonDatabase.getPokemonDao().getPokemon() }
        return Pager(
            config = PagingConfig(pageSize = 20),
            remoteMediator = PokemonRemoteMediator(
                pokemonApi,
                pokemonDatabase
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }
}