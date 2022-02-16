package com.muhammadarifan.pokemonboilerplate.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.muhammadarifan.pokemonboilerplate.local.dao.*
import com.muhammadarifan.pokemonboilerplate.local.model.*

@Database(
    entities = [
        Pokemon::class,
        PokemonDetail::class,
        PokemonEvolution::class,
        PokemonRemoteKey::class,
        PokemonStat::class,
        PokemonType::class
    ],
    version = 3
)
abstract class PokemonDatabase : RoomDatabase(){
    abstract fun getPokemonDao() : PokemonDao
    abstract fun getPokemonRemoteKeyDao() : PokemonRemoteKeyDao
    abstract fun getPokemonDetailDao() : PokemonDetailDao
    abstract fun getPokemonEvolutionDao() : PokemonEvolutionDao
    abstract fun getPokemonStatDao() : PokemonStatDao
    abstract fun getPokemonTypeDao() : PokemonTypeDao
}