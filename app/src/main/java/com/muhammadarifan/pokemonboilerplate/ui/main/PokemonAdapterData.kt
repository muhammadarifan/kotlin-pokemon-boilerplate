package com.muhammadarifan.pokemonboilerplate.ui.main

data class PokemonAdapterData(
    val id: String,
    val name: String,
    val color: String,
    val type: List<Type>
)

data class Type(
    val name: String
)
