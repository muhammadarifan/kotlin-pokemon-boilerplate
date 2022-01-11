package com.muhammadarifan.klikdigitaltask.local.repository

import com.muhammadarifan.klikdigitaltask.local.dao.PokemonDao
import com.muhammadarifan.klikdigitaltask.local.model.Pokemon
import javax.inject.Inject

class PokemonRepository @Inject constructor(
    private val pokemonDao: PokemonDao,
){
    suspend fun postPokemon(pokemon: Pokemon) =
        pokemonDao.postPokemon(pokemon)

    suspend fun getPokemon() =
        pokemonDao.getPokemon()

    suspend fun getPokemonById(id : Int) =
        pokemonDao.getPokemonById(id)
}