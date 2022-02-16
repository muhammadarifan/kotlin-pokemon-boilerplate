package com.muhammadarifan.pokemonboilerplate.remote.model.get_pokemon_evolution_chain

data class PokemonEvolutionChainResponse(
    val baby_trigger_item: Any,
    val chain: Chain,
    val id: Int
)