package com.muhammadarifan.klikdigitaltask.remote.model.get_pokemon_evolution_chain

data class Chain(
    val evolution_details: List<Any>,
    val evolves_to: List<EvolvesTo>,
    val is_baby: Boolean,
    val species: SpeciesXX
)