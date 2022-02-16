package com.muhammadarifan.pokemonboilerplate.remote.model.get_pokemon_detail

import com.google.gson.annotations.SerializedName

data class GenerationIii(
    val emerald: Emerald,
    @SerializedName("firered-leafgreen") val firered_leafgreen: FireredLeafgreen,
    @SerializedName("ruby-sapphire") val ruby_sapphire: RubySapphire
)