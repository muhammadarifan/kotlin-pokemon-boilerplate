package com.muhammadarifan.klikdigitaltask.remote.repository

import com.muhammadarifan.klikdigitaltask.remote.PokemonApi
import com.muhammadarifan.klikdigitaltask.remote.model.get_pokemon_detail.PokemonDetailResponse
import com.muhammadarifan.klikdigitaltask.remote.model.get_pokemon_evolution_chain.PokemonEvolutionChainResponse
import com.muhammadarifan.klikdigitaltask.remote.model.get_pokemon_species.PokemonSpeciesResponse
import com.muhammadarifan.klikdigitaltask.util.Resource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PokemonRepository @Inject constructor(
    private val pokemonApi: PokemonApi
) {
    suspend fun getPokemonDetail(pokemon_id: Int): Resource<PokemonDetailResponse> {
        val response = try {
            pokemonApi.getPokemonDetail(pokemon_id)
        } catch (e: Exception) {
            return Resource.Error(e.message ?: "An unknown error eccured.")
        }
        return Resource.Success(response)
    }

    suspend fun getPokemonSpecies(pokemon_id: Int): Resource<PokemonSpeciesResponse> {
        val response = try {
            pokemonApi.getPokemonSpecies(pokemon_id)
        } catch (e: Exception) {
            return Resource.Error(e.message ?: "An unknown error eccured.")
        }
        return Resource.Success(response)
    }

    suspend fun getPokemonEvolutionChain(evolution_chain_id: Int): Resource<PokemonEvolutionChainResponse> {
        val response = try {
            pokemonApi.getPokemonEvolutionChain(evolution_chain_id)
        } catch (e: Exception) {
            return Resource.Error(e.message ?: "An unknown error eccured.")
        }
        return Resource.Success(response)
    }
}