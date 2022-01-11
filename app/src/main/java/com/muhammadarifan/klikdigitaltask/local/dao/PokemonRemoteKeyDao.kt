package com.muhammadarifan.klikdigitaltask.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.muhammadarifan.klikdigitaltask.local.model.PokemonRemoteKey

@Dao
interface PokemonRemoteKeyDao {
    @Query("SELECT * FROM pokemon_remote_key_table WHERE id = :id")
    suspend fun getRemoteKey(id : String) : PokemonRemoteKey

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun postAllRemoteKey(remoteKeys : List<PokemonRemoteKey>)

    @Query("DELETE FROM pokemon_remote_key_table")
    suspend fun deleteRemoteKey()
}