package com.muhammadarifan.pokemonboilerplate.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.muhammadarifan.pokemonboilerplate.ui.detail.sub_ui.pokemon_evolution.PokemonEvolutionFragment
import com.muhammadarifan.pokemonboilerplate.ui.detail.sub_ui.pokemon_information.PokemonInformationFragment

class DetailViewPagerAdapter(
    private val pokemon_id: Int,
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle
): FragmentStateAdapter(fragmentManager, lifecycle){
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        var fragment = Fragment()
        when(position) {
            0 -> {
                fragment = PokemonInformationFragment()
                val bundle = Bundle()
                bundle.putInt("pokemon_id", pokemon_id)
                fragment.arguments = bundle
                return fragment
            }
            1 -> {
                fragment = PokemonEvolutionFragment()
                val bundle = Bundle()
                bundle.putInt("pokemon_id", pokemon_id)
                fragment.arguments = bundle
                return fragment
            }
        }
        return fragment
    }
}