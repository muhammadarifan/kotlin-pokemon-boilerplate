package com.muhammadarifan.pokemonboilerplate.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pokemon_detail_table")
data class PokemonDetail(
    @PrimaryKey(autoGenerate = false)
    var id : String,
    var pokemon_id : Int,
    var name : String?,
    var height : Int?,
    var weight : Int?,
    var color : String?,
)