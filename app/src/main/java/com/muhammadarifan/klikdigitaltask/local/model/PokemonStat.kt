package com.muhammadarifan.klikdigitaltask.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "pokemon_stat_table")
data class PokemonStat(
    @PrimaryKey(autoGenerate = false)
    var id : String,
    var pokemon_id : Int,
    var stat_name : String?,
    var stat_value : String?
) : Serializable