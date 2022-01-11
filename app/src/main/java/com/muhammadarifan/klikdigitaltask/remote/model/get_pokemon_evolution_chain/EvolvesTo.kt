package com.muhammadarifan.klikdigitaltask.remote.model.get_pokemon_evolution_chain

data class EvolvesTo(
    val evolution_details: List<EvolutionDetail>,
    val evolves_to: List<EvolvesToX>,
    val is_baby: Boolean,
    val species: SpeciesX
)