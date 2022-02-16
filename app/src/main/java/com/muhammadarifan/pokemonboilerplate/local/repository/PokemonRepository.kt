package com.muhammadarifan.pokemonboilerplate.local.repository

import com.muhammadarifan.pokemonboilerplate.local.dao.PokemonDao
import com.muhammadarifan.pokemonboilerplate.local.model.Pokemon
import javax.inject.Inject

class PokemonRepository @Inject constructor(
    private val pokemonDao: PokemonDao,
){
    suspend fun postPokemon(pokemon: Pokemon) =
        pokemonDao.postPokemon(pokemon)

    fun getPokemon() =
        pokemonDao.getPokemon()

    suspend fun getPokemonById(id : Int) =
        pokemonDao.getPokemonById(id)

    suspend fun deletePokemon() =
        pokemonDao.deletePokemon()
}