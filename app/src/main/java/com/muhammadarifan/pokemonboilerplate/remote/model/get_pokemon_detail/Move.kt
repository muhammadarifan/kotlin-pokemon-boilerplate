package com.muhammadarifan.pokemonboilerplate.remote.model.get_pokemon_detail

data class Move(
    val move: MoveX,
    val version_group_details: List<VersionGroupDetail>
)