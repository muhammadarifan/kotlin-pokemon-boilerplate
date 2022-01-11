package com.muhammadarifan.klikdigitaltask.remote

import com.muhammadarifan.klikdigitaltask.local.model.PokemonEvolution
import com.muhammadarifan.klikdigitaltask.remote.model.get_pokemon.PokemonResponse
import com.muhammadarifan.klikdigitaltask.remote.model.get_pokemon_detail.PokemonDetailResponse
import com.muhammadarifan.klikdigitaltask.remote.model.get_pokemon_evolution_chain.PokemonEvolutionChainResponse
import com.muhammadarifan.klikdigitaltask.remote.model.get_pokemon_species.PokemonSpeciesResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonApi {
    companion object {
        const val BASE_URL = "https://pokeapi.co/api/v2/"
    }

    @GET("pokemon")
    suspend fun getPokemon(
        @Query("offset") offset : Int,
        @Query("limit") limit : Int
    ) : PokemonResponse

    @GET("pokemon/{pokemon_id}")
    suspend fun getPokemonDetail(
        @Path("pokemon_id") pokemon_id : Int
    ) : PokemonDetailResponse

    @GET("pokemon-species/{pokemon_id}")
    suspend fun getPokemonSpecies(
        @Path("pokemon_id") pokemon_id : Int
    ) : PokemonSpeciesResponse

    @GET("evolution-chain/{evolution_chain_id}")
    suspend fun getPokemonEvolutionChain(
        @Path("evolution_chain_id") evolution_chain_id : Int
    ) : PokemonEvolutionChainResponse
}