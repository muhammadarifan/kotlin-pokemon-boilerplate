package com.muhammadarifan.klikdigitaltask.local.repository

import com.muhammadarifan.klikdigitaltask.local.dao.PokemonTypeDao
import com.muhammadarifan.klikdigitaltask.local.model.PokemonType
import javax.inject.Inject

class PokemonTypeRepository @Inject constructor(
    private val pokemonTypeDao: PokemonTypeDao
) {
    suspend fun postPokemonType(pokemonType: PokemonType) =
        pokemonTypeDao.postPokemonType(pokemonType)

    suspend fun getPokemonTypeByPokemonId(pokemon_id: Int) =
        pokemonTypeDao.getPokemonTypeByPokemonId(pokemon_id)

    suspend fun deletePokemonTypeByPokemonId(pokemon_id: Int) =
        pokemonTypeDao.deletePokemonTypeByPokemonId(pokemon_id)
}