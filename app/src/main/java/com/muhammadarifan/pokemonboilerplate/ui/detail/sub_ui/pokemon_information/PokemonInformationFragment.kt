package com.muhammadarifan.pokemonboilerplate.ui.detail.sub_ui.pokemon_information

import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.muhammadarifan.pokemonboilerplate.R
import com.muhammadarifan.pokemonboilerplate.databinding.FragmentPokemonInformationBinding
import com.muhammadarifan.pokemonboilerplate.ui.detail.DetailViewModel
import com.muhammadarifan.pokemonboilerplate.util.Variable
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PokemonInformationFragment : Fragment(R.layout.fragment_pokemon_information) {
    private lateinit var binding: FragmentPokemonInformationBinding
    private val viewModel by viewModels<DetailViewModel>()

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val pokemonID = requireArguments().getInt("pokemon_id")
        binding = FragmentPokemonInformationBinding.bind(view)

        val adapter = StatAdapter(mutableListOf())
        binding.rvStat.adapter = adapter

        viewModel.connectionCheck(requireContext())
        lifecycleScope.launch {
            viewModel.getPokemonDetailFromLocal(pokemonID)
            viewModel.getPokemonTypeFromLocal(pokemonID)
            viewModel.getPokemonStatFromLocal(pokemonID)
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
                    viewModel.getPokemonDetailFromLocal(pokemonID)
                    viewModel.getPokemonTypeFromLocal(pokemonID)
                    viewModel.getPokemonStatFromLocal(pokemonID)
                }
            }
        })

        viewModel.pokemonDetail.observe(viewLifecycleOwner, {
            if (it != null) {
                binding.apply {
                    Glide.with(view)
                        .load(Variable.POKEMON_OFFICIAL_ART_URL + it.pokemon_id + ".png")
                        .centerCrop()
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(ivPokemon)
                    tvName.text = it.name
                    tvHeight.text = it.height.toString()
                    tvWeight.text = it.weight.toString()
                    tvColor.text = it.color
                }
            }
        })

        viewModel.pokemonType.observe(viewLifecycleOwner, {
            if (!it.isNullOrEmpty()) {
                val datas = it.map { type ->
                    type.type_name
                }

                val data = datas.joinToString(", ")
                binding.apply {
                    tvType.text = data
                }
            }
        })

        viewModel.pokemonStat.observe(viewLifecycleOwner, {
            if (!it.isNullOrEmpty()) {
                binding.apply {
                    if (it.isNotEmpty()) {
                        adapter.data = listOf()
                        adapter.data = it
                        adapter.notifyDataSetChanged()
                    } else {
                        adapter.data = listOf()
                        adapter.notifyDataSetChanged()
                    }
                }
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