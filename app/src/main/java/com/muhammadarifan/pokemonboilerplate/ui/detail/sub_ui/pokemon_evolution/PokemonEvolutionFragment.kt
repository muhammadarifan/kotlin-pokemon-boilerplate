package com.muhammadarifan.pokemonboilerplate.ui.detail.sub_ui.pokemon_evolution

import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.muhammadarifan.pokemonboilerplate.R
import com.muhammadarifan.pokemonboilerplate.databinding.FragmentPokemonEvolutionBinding
import com.muhammadarifan.pokemonboilerplate.ui.detail.DetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PokemonEvolutionFragment : Fragment(R.layout.fragment_pokemon_evolution) {
    private lateinit var binding: FragmentPokemonEvolutionBinding
    private val viewModel by viewModels<DetailViewModel>()

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val pokemonID = requireArguments().getInt("pokemon_id")
        binding = FragmentPokemonEvolutionBinding.bind(view)

        val adapter = EvolutionAdapter(mutableListOf())
        binding.rvPokemon.adapter = adapter

        viewModel.connectionCheck(requireContext())
        lifecycleScope.launch {
            viewModel.getPokemonEvolutionFromLocal(pokemonID)
        }

        viewModel.isConnect.observe(viewLifecycleOwner, {
            if (it) {
                lifecycleScope.launch {
                    viewModel.getPokemonDetailFromRemote(pokemonID)
                }
            }
        })

        viewModel.isRemoteFinish.observe(viewLifecycleOwner, {
            if (it) {
                lifecycleScope.launch {
                    viewModel.getPokemonEvolutionFromLocal(pokemonID)
                }
            }
        })

        viewModel.pokemonEvolution.observe(viewLifecycleOwner, {
            if (it.isNotEmpty()) {
                adapter.data = listOf()
                adapter.data = it
                adapter.notifyDataSetChanged()
            } else {
                adapter.data = listOf()
                adapter.notifyDataSetChanged()
            }
        })

        viewModel.loadError.observe(viewLifecycleOwner, {
            if (it != "") {
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            }
        })

        viewModel.isLoading.observe(viewLifecycleOwner, {
            if (it) {
                binding.pbLoading.visibility = View.VISIBLE
            } else {
                binding.pbLoading.visibility = View.INVISIBLE
            }
        })
    }
}