package com.muhammadarifan.klikdigitaltask.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.muhammadarifan.klikdigitaltask.local.model.PokemonDetail

@Dao
interface PokemonDetailDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun postPokemonDetail(pokemonDetail: PokemonDetail)

    @Query("SELECT * FROM pokemon_detail_table WHERE pokemon_id = :pokemon_id")
    suspend fun getPokemonDetailByPokemonId(pokemon_id : Int) : PokemonDetail

    @Query("DELETE FROM pokemon_detail_table WHERE pokemon_id = :pokemon_id")
    suspend fun deletePokemonDetailByPokemonId(pokemon_id : Int)
}