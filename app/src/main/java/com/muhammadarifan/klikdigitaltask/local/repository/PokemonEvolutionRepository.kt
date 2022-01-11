package com.muhammadarifan.klikdigitaltask.local.repository

import com.muhammadarifan.klikdigitaltask.local.dao.PokemonEvolutionDao
import com.muhammadarifan.klikdigitaltask.local.dao.PokemonStatDao
import com.muhammadarifan.klikdigitaltask.local.dao.PokemonTypeDao
import com.muhammadarifan.klikdigitaltask.local.model.PokemonEvolution
import javax.inject.Inject

class PokemonEvolutionRepository @Inject constructor(
    private val pokemonEvolutionDao: PokemonEvolutionDao,
    private val pokemonStatDao: PokemonStatDao,
    private val pokemonTypeDao: PokemonTypeDao
) {
    suspend fun postPokemonEvolution(pokemonEvolution: PokemonEvolution) =
        pokemonEvolutionDao.postPokemonEvolution(pokemonEvolution)

    suspend fun getPokemonEvolutionByPokemonId(pokemon_id: Int) =
        pokemonEvolutionDao.getPokemonEvolutionByPokemonId(pokemon_id)

    suspend fun deletePokemonEvolutionByPokemonId(pokemon_id: Int) =
        pokemonEvolutionDao.deletePokemonEvolutionByPokemonId(pokemon_id)
}