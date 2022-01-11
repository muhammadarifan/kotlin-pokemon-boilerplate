package com.muhammadarifan.klikdigitaltask.remote.model.get_pokemon_detail

data class HeldItem(
    val item: Item,
    val version_details: List<VersionDetail>
)