package com.muhammadarifan.pokemonboilerplate.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.muhammadarifan.pokemonboilerplate.local.model.PokemonEvolution

@Dao
interface PokemonEvolutionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun postPokemonEvolution(pokemonDetail: PokemonEvolution)

    @Query("SELECT * FROM pokemon_evolution_table WHERE pokemon_id = :pokemon_id")
    suspend fun getPokemonEvolutionByPokemonId(pokemon_id : Int) : List<PokemonEvolution>

    @Query("DELETE FROM pokemon_evolution_table WHERE pokemon_id = :pokemon_id")
    suspend fun deletePokemonEvolutionByPokemonId(pokemon_id : Int)
}