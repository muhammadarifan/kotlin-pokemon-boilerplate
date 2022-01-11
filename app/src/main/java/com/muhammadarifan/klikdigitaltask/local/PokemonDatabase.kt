package com.muhammadarifan.klikdigitaltask.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.muhammadarifan.klikdigitaltask.local.dao.*
import com.muhammadarifan.klikdigitaltask.local.model.*

@Database(
    entities = [
        Pokemon::class,
        PokemonDetail::class,
        PokemonEvolution::class,
        PokemonRemoteKey::class,
        PokemonStat::class,
        PokemonType::class
    ],
    version = 1
)
abstract class PokemonDatabase : RoomDatabase(){
    abstract fun getPokemonDao() : PokemonDao
    abstract fun getPokemonRemoteKeyDao() : PokemonRemoteKeyDao
    abstract fun getPokemonDetailDao() : PokemonDetailDao
    abstract fun getPokemonEvolutionDao() : PokemonEvolutionDao
    abstract fun getPokemonStatDao() : PokemonStatDao
    abstract fun getPokemonTypeDao() : PokemonTypeDao
}