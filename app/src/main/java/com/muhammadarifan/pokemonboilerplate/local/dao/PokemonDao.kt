package com.muhammadarifan.pokemonboilerplate.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.muhammadarifan.pokemonboilerplate.local.model.Pokemon

@Dao
interface PokemonDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun postPokemon(pokemon : Pokemon)

    @Query("SELECT * FROM pokemon_table")
    fun getPokemon() : PagingSource<Int, Pokemon>

    @Query("SELECT * FROM pokemon_table WHERE id = :id")
    suspend fun getPokemonById(id : Int) : Pokemon

    @Query("DELETE FROM pokemon_table")
    suspend fun deletePokemon()
}