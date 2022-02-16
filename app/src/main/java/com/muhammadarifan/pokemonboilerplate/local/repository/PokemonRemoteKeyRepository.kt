package com.muhammadarifan.pokemonboilerplate.local.repository

import com.muhammadarifan.pokemonboilerplate.local.dao.PokemonRemoteKeyDao
import com.muhammadarifan.pokemonboilerplate.local.model.PokemonRemoteKey
import javax.inject.Inject

class PokemonRemoteKeyRepository @Inject constructor(
    private val pokemonRemoteKeyDao: PokemonRemoteKeyDao,
){
    suspend fun getRemoteKey(id: String) =
        pokemonRemoteKeyDao.getRemoteKey(id)

    suspend fun postRemoteKey(remoteKeys : List<PokemonRemoteKey>) =
        pokemonRemoteKeyDao.postAllRemoteKey(remoteKeys)

    suspend fun deleteRemoteKey() =
        pokemonRemoteKeyDao.deleteRemoteKey()
}