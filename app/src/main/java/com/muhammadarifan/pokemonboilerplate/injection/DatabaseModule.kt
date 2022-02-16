package com.muhammadarifan.pokemonboilerplate.injection

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.muhammadarifan.pokemonboilerplate.local.PokemonDatabase
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
    ): PokemonDatabase {
        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE pokemon_table ADD COLUMN color TEXT")
            }
        }

        val MIGRATION_2_3 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE pokemon_table ADD COLUMN types TEXT")
            }
        }

        return Room.databaseBuilder(
            context,
            PokemonDatabase::class.java,
            "pokemon_db"
        )
            .addMigrations(MIGRATION_1_2, MIGRATION_2_3)
            .build()
    }

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