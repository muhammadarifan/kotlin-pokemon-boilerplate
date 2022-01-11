package com.muhammadarifan.klikdigitaltask.remote.model.get_pokemon_detail

import com.google.gson.annotations.SerializedName

data class GenerationV(
    @SerializedName("black-white") val black_white: BlackWhite
)