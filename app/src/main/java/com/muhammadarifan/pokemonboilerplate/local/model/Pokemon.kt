package com.muhammadarifan.pokemonboilerplate.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "pokemon_table")
data class Pokemon(
    @PrimaryKey(autoGenerate = false)
    var id : Int,
    var name : String?,
    var color : String?,
    var types : String?,
) : Serializable