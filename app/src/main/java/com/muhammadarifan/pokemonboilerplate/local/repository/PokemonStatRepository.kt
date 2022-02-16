package com.muhammadarifan.pokemonboilerplate.local.repository

import com.muhammadarifan.pokemonboilerplate.local.dao.PokemonStatDao
import com.muhammadarifan.pokemonboilerplate.local.model.PokemonStat
import javax.inject.Inject

class PokemonStatRepository @Inject constructor(
    private val pokemonStatDao: PokemonStatDao
) {
    suspend fun postPokemonStat(pokemonStat: PokemonStat) =
        pokemonStatDao.postPokemonStat(pokemonStat)

    suspend fun getPokemonStatByPokemonId(pokemon_id: Int) =
        pokemonStatDao.getPokemonStatByPokemonId(pokemon_id)

    suspend fun deletePokemonStatByPokemonId(pokemon_id: Int) =
        pokemonStatDao.deletePokemonStatByPokemonId(pokemon_id)
}