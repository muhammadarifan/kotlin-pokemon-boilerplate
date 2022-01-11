package com.muhammadarifan.klikdigitaltask.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pokemon_evolution_table")
data class PokemonEvolution(
    @PrimaryKey(autoGenerate = false)
    var id : String,
    var pokemon_id : Int,
    var name : String?,
    var evolution_pokemon_id: Int
)