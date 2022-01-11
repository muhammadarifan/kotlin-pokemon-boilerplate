package com.muhammadarifan.klikdigitaltask.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.muhammadarifan.klikdigitaltask.local.model.PokemonStat

@Dao
interface PokemonStatDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun postPokemonStat(pokemonStat: PokemonStat)

    @Query("SELECT * FROM pokemon_stat_table WHERE pokemon_id = :pokemon_id")
    suspend fun getPokemonStatByPokemonId(pokemon_id : Int) : List<PokemonStat>

    @Query("DELETE FROM pokemon_stat_table WHERE pokemon_id = :pokemon_id")
    suspend fun deletePokemonStatByPokemonId(pokemon_id : Int)
}