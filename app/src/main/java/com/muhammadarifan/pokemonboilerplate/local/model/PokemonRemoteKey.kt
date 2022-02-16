package com.muhammadarifan.pokemonboilerplate.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pokemon_remote_key_table")
data class PokemonRemoteKey(
    @PrimaryKey(autoGenerate = false)
    val id : String,
    val next : Int?,
    val previous : Int?
)