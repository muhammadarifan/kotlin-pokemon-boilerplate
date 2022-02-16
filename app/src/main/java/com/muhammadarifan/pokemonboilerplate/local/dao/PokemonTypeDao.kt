package com.muhammadarifan.pokemonboilerplate.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.muhammadarifan.pokemonboilerplate.local.model.PokemonType

@Dao
interface PokemonTypeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun postPokemonType(pokemonDetail: PokemonType)

    @Query("SELECT * FROM pokemon_type_table WHERE pokemon_id = :pokemon_id")
    suspend fun getPokemonTypeByPokemonId(pokemon_id : Int) : List<PokemonType>

    @Query("DELETE FROM pokemon_type_table WHERE pokemon_id = :pokemon_id")
    suspend fun deletePokemonTypeByPokemonId(pokemon_id : Int)
}