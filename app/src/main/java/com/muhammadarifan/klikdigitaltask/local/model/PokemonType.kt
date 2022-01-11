package com.muhammadarifan.klikdigitaltask.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "pokemon_type_table")
data class PokemonType(
    @PrimaryKey(autoGenerate = false)
    var id : String,
    var pokemon_id : Int,
    var type_name : String?,
) : Serializable