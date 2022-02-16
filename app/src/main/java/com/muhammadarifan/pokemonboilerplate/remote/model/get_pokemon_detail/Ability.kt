package com.muhammadarifan.pokemonboilerplate.remote.model.get_pokemon_detail

data class Ability(
    val ability: AbilityX,
    val is_hidden: Boolean,
    val slot: Int
)