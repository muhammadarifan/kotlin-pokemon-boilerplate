package com.muhammadarifan.klikdigitaltask.remote.model.get_pokemon_detail

import com.google.gson.annotations.SerializedName

data class Other(
    val dream_world: DreamWorld,
    val home: Home,
    @SerializedName("official-artwork") val official_artwork: OfficialArtwork
)