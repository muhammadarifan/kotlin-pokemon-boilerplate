package com.muhammadarifan.klikdigitaltask.ui.main

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.ExperimentalPagingApi
import com.muhammadarifan.klikdigitaltask.pagination.repository.PokemonPagingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@ExperimentalPagingApi
@HiltViewModel
class MainViewModel @Inject constructor (
    pokemonPagingRepository: PokemonPagingRepository,
) : ViewModel(){
    val pokemon = pokemonPagingRepository.getPokemon()
    val isConnect = MutableLiveData(true)

    @RequiresApi(Build.VERSION_CODES.M)
    fun connectionCheck(context: Context) {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                    isConnect.value = true
                }
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                    isConnect.value = true
                }
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                    isConnect.value = true
                }
                else -> {
                    isConnect.value = false
                }
            }
        } else {
            isConnect.value = false
        }
    }
}