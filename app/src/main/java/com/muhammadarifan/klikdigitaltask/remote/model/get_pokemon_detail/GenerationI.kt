package com.muhammadarifan.klikdigitaltask.remote.model.get_pokemon_detail

import com.google.gson.annotations.SerializedName

data class GenerationI(
    @SerializedName("red-blue") val red_blue: RedBlue,
    val yellow: Yellow
)