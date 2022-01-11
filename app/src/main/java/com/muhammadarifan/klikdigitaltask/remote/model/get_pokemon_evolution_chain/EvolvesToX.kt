package com.muhammadarifan.klikdigitaltask.remote.model.get_pokemon_evolution_chain

data class EvolvesToX(
    val evolution_details: List<EvolutionDetailX>,
    val evolves_to: List<Any>,
    val is_baby: Boolean,
    val species: Species
)