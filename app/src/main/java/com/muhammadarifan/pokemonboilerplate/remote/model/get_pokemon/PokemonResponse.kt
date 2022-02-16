package com.muhammadarifan.pokemonboilerplate.remote.model.get_pokemon

data class PokemonResponse(
    val count: Int,
    val next: String,
    val previous: String?,
    val results: List<Pokemon?>
)