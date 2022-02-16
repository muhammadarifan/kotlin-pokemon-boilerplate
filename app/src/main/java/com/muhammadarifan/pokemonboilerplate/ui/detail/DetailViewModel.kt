package com.muhammadarifan.pokemonboilerplate.ui.detail

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muhammadarifan.pokemonboilerplate.local.model.PokemonDetail
import com.muhammadarifan.pokemonboilerplate.local.model.PokemonEvolution
import com.muhammadarifan.pokemonboilerplate.local.model.PokemonStat
import com.muhammadarifan.pokemonboilerplate.local.model.PokemonType
import com.muhammadarifan.pokemonboilerplate.local.repository.PokemonDetailRepository
import com.muhammadarifan.pokemonboilerplate.local.repository.PokemonEvolutionRepository
import com.muhammadarifan.pokemonboilerplate.local.repository.PokemonStatRepository
import com.muhammadarifan.pokemonboilerplate.local.repository.PokemonTypeRepository
import com.muhammadarifan.pokemonboilerplate.remote.repository.PokemonRepository
import com.muhammadarifan.pokemonboilerplate.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val localPokemonDetailRepository: PokemonDetailRepository,
    private val localPokemonEvolutionRepository: PokemonEvolutionRepository,
    private val localPokemonStatRepository: PokemonStatRepository,
    private val localPokemonTypeRepository: PokemonTypeRepository,
    private val remotePokemonRepository: PokemonRepository
) : ViewModel() {
    val loadError = MutableLiveData("")
    val isLoading = MutableLiveData(false)
    val isConnect = MutableLiveData(true)
    val isRemoteFinish = MutableLiveData(false)

    var pokemonEvolution: MutableLiveData<List<PokemonEvolution>> = MutableLiveData()
    var pokemonDetail: MutableLiveData<PokemonDetail> = MutableLiveData()
    var pokemonType: MutableLiveData<List<PokemonType>> = MutableLiveData()
    var pokemonStat: MutableLiveData<List<PokemonStat>> = MutableLiveData()

    suspend fun getPokemonDetailFromLocal(pokemon_id: Int) {
        val response = localPokemonDetailRepository.getPokemonDetailByPokemonId(pokemon_id)
        pokemonDetail.value = response
    }

    suspend fun getPokemonEvolutionFromLocal(pokemon_id: Int) {
        val response = localPokemonEvolutionRepository.getPokemonEvolutionByPokemonId(pokemon_id)
        pokemonEvolution.value = response
    }

    suspend fun getPokemonStatFromLocal(pokemon_id: Int) {
        val response = localPokemonStatRepository.getPokemonStatByPokemonId(pokemon_id)
        pokemonStat.value = response
    }

    suspend fun getPokemonTypeFromLocal(pokemon_id: Int) {
        val response = localPokemonTypeRepository.getPokemonTypeByPokemonId(pokemon_id)
        pokemonType.value = response
    }

    suspend fun getPokemonDetailFromRemote(pokemon_id: Int) {
        isLoading.value = true

        isRemoteFinish.value = false

        viewModelScope.launch {
            when(val detailResult = remotePokemonRepository.getPokemonDetail(pokemon_id)) {
                is Resource.Success -> {
                    val pokemonDetail = detailResult.data!!
                    when (val speciesResult = remotePokemonRepository.getPokemonSpecies(pokemon_id)) {
                        is Resource.Success -> {
                            val pokemonSpecies = speciesResult.data
                            when(val evolutionResult = remotePokemonRepository.getPokemonEvolutionChain(pokemonSpecies!!.evolution_chain.url.dropLast(1).takeLastWhile { it.isDigit() }.toInt())) {
                                is Resource.Success -> {
                                    val pokemonEvolution = evolutionResult.data
                                    localPokemonDetailRepository.postPokemonDetail(PokemonDetail(
                                        id = pokemonDetail.name + pokemonDetail.id,
                                        pokemon_id = pokemonDetail.id,
                                        name = pokemonDetail.name,
                                        height = pokemonDetail.height,
                                        weight = pokemonDetail.weight,
                                        color = pokemonSpecies.color.name
                                    ))
                                    pokemonDetail.stats.forEach {
                                        localPokemonStatRepository.postPokemonStat(PokemonStat(
                                            id = it.stat.name + pokemonDetail.id,
                                            pokemon_id = pokemonDetail.id,
                                            stat_name = it.stat.name,
                                            stat_value = (it.base_stat + it.effort).toString()
                                        ))
                                    }
                                    pokemonDetail.types.forEach {
                                        val name = it.type.name
                                        localPokemonTypeRepository.postPokemonType(PokemonType(
                                            id = name + pokemonDetail.id,
                                            pokemon_id = pokemonDetail.id,
                                            type_name = name
                                        ))
                                    }
                                    localPokemonEvolutionRepository.postPokemonEvolution(
                                        PokemonEvolution(
                                            id = pokemonEvolution!!.chain.species.name + pokemonDetail.id,
                                            pokemon_id = pokemonDetail.id,
                                            name = pokemonEvolution.chain.species.name,
                                            evolution_pokemon_id = pokemonEvolution.chain.species.url.dropLast(1).takeLastWhile { it.isDigit() }.toInt()
                                        )
                                    )
                                    if (pokemonEvolution.chain.evolves_to.isNotEmpty()) {
                                        localPokemonEvolutionRepository.postPokemonEvolution(
                                            PokemonEvolution(
                                                id = pokemonEvolution.chain.evolves_to.first().species.name + pokemonDetail.id,
                                                pokemon_id = pokemonDetail.id,
                                                name = pokemonEvolution.chain.evolves_to.first().species.name,
                                                evolution_pokemon_id = pokemonEvolution.chain.evolves_to.first().species.url.dropLast(1).takeLastWhile { it.isDigit() }.toInt()
                                            )
                                        )
                                        if (pokemonEvolution.chain.evolves_to.first().evolves_to.isNotEmpty()) {
                                            localPokemonEvolutionRepository.postPokemonEvolution(
                                                PokemonEvolution(
                                                    id = pokemonEvolution.chain.evolves_to.first().evolves_to.first().species.name + pokemonDetail.id,
                                                    pokemon_id = pokemonDetail.id,
                                                    name = pokemonEvolution.chain.evolves_to.first().evolves_to.first().species.name,
                                                    evolution_pokemon_id = pokemonEvolution.chain.evolves_to.first().evolves_to.first().species.url.dropLast(1).takeLastWhile { it.isDigit() }.toInt()
                                                )
                                            )
                                        }
                                    }

                                    loadError.value = ""
                                    isLoading.value = false
                                    isRemoteFinish.value = true
                                }
                                is Resource.Error -> {
                                    loadError.value = evolutionResult.message
                                    isLoading.value = false
                                    isRemoteFinish.value = false
                                }
                                is Resource.Loading -> {
                                    isLoading.value = true
                                    isRemoteFinish.value = false
                                }
                            }
                        }
                        is Resource.Error -> {
                            loadError.value = speciesResult.message
                            isLoading.value = false
                            isRemoteFinish.value = false
                        }
                        is Resource.Loading -> {
                            isLoading.value = true
                            isRemoteFinish.value = false
                        }
                    }
                }
                is Resource.Error -> {
                    loadError.value = detailResult.message
                    isLoading.value = false
                    isRemoteFinish.value = false
                }
                is Resource.Loading -> {
                    isLoading.value = true
                    isRemoteFinish.value = false
                }
            }
        }
    }

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