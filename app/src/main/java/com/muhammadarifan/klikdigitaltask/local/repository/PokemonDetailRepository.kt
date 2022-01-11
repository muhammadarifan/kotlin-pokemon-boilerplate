package com.muhammadarifan.klikdigitaltask.local.repository

import com.muhammadarifan.klikdigitaltask.local.dao.*
import com.muhammadarifan.klikdigitaltask.local.model.PokemonDetail
import javax.inject.Inject

class PokemonDetailRepository @Inject constructor(
    private val pokemonDetailDao: PokemonDetailDao,
) {
    suspend fun postPokemonDetail(pokemonDetail: PokemonDetail) =
        pokemonDetailDao.postPokemonDetail(pokemonDetail)

    suspend fun getPokemonDetailByPokemonId(pokemon_id: Int) =
        pokemonDetailDao.getPokemonDetailByPokemonId(pokemon_id)

    suspend fun deletePokemonByPokemonId(pokemon_id: Int) =
        pokemonDetailDao.deletePokemonDetailByPokemonId(pokemon_id)
}