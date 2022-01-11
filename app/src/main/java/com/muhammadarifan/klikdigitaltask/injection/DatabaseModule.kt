package com.muhammadarifan.klikdigitaltask.injection

import android.content.Context
import androidx.room.Room
import com.muhammadarifan.klikdigitaltask.local.PokemonDatabase
import com.muhammadarifan.klikdigitaltask.local.dao.PokemonDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideRoomDatabase(
        @ApplicationContext context : Context
    ) =
        Room.databaseBuilder(
            context,
            PokemonDatabase::class.java,
            "pokemon_db"
        ).build()

    @Provides
    @Singleton
    fun providePokemonDao(db : PokemonDatabase) =
        db.getPokemonDao()

    @Provides
    @Singleton
    fun providePokemonDetailDao(db : PokemonDatabase) =
        db.getPokemonDetailDao()

    @Provides
    @Singleton
    fun providePokemonEvolutionDao(db : PokemonDatabase) =
        db.getPokemonEvolutionDao()

    @Provides
    @Singleton
    fun providePokemonRemoteKeyDao(db : PokemonDatabase) =
        db.getPokemonRemoteKeyDao()

    @Provides
    @Singleton
    fun providePokemonStatDao(db : PokemonDatabase) =
        db.getPokemonStatDao()

    @Provides
    @Singleton
    fun providePokemonTypeDao(db : PokemonDatabase) =
        db.getPokemonTypeDao()
}