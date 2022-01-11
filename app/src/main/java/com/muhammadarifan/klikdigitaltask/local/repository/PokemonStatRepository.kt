package com.muhammadarifan.klikdigitaltask.local.repository

import com.muhammadarifan.klikdigitaltask.local.dao.PokemonStatDao
import com.muhammadarifan.klikdigitaltask.local.model.PokemonStat
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