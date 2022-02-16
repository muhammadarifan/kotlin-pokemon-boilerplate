package com.muhammadarifan.pokemonboilerplate.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.muhammadarifan.pokemonboilerplate.R
import com.muhammadarifan.pokemonboilerplate.databinding.ItemPokemonListBinding
import com.muhammadarifan.pokemonboilerplate.local.model.Pokemon

class MainAdapter(private val listener : OnItemClickListener) : PagingDataAdapter<Pokemon, MainAdapter.PokemonViewHolder>(COMPARATOR) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val binding = ItemPokemonListBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return PokemonViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }

    inner class PokemonViewHolder(private val binding: ItemPokemonListBinding)
        : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition

                if (position != RecyclerView.NO_POSITION) {
                    val item = getItem(position)

                    if (item != null) {
                        listener.onItemClick(item)
                    }
                }
            }
        }

        fun bind(pokemon: Pokemon) {
            with(binding) {
                when (pokemon.color) {
                    "black" -> {
                        vBackground.setBackgroundResource(R.drawable.pokemon_background_radius_blank)
                    }
                    "blue" -> {
                        vBackground.setBackgroundResource(R.drawable.pokemon_background_radius_blue)
                    }
                    "brown" -> {
                        vBackground.setBackgroundResource(R.drawable.pokemon_background_radius_brown)
                    }
                    "gray" -> {
                        vBackground.setBackgroundResource(R.drawable.pokemon_background_radius_gray)
                    }
                    "green" -> {
                        vBackground.setBackgroundResource(R.drawable.pokemon_background_radius_green)
                    }
                    "pink" -> {
                        vBackground.setBackgroundResource(R.drawable.pokemon_background_radius_pink)
                    }
                    "purple" -> {
                        vBackground.setBackgroundResource(R.drawable.pokemon_background_radius_purple)
                    }
                    "red" -> {
                        vBackground.setBackgroundResource(R.drawable.pokemon_background_radius_red)
                    }
                    "white" -> {
                        vBackground.setBackgroundResource(R.drawable.pokemon_background_radius_white)
                    }
                    "yellow" -> {
                        vBackground.setBackgroundResource(R.drawable.pokemon_background_radius_yellow)
                    }
                }

                Glide.with(itemView)
                    .load(Variable.POKEMON_OFFICIAL_ART_URL + pokemon.id + ".png")
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(ivOfficialArtwork)

                tvName.text = pokemon.name
            }
        }
    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<Pokemon>() {
            override fun areItemsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean {
                return oldItem == newItem
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(pokemon: Pokemon)
    }
}