package com.muhammadarifan.klikdigitaltask.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.muhammadarifan.klikdigitaltask.databinding.ItemPokemonListBinding
import com.muhammadarifan.klikdigitaltask.local.model.Pokemon
import com.muhammadarifan.klikdigitaltask.util.Variable

class MainAdapter(private val listener : OnItemClickListener) : PagingDataAdapter<Pokemon, MainAdapter.PokemonViewHolder>(COMPARATOR) {
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
                Glide.with(itemView)
                    .load(Variable.POKEMON_OFFICIAL_ART_URL + pokemon.id + ".png")
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(ivOfficialArtwork)

                tvName.text = pokemon.name
            }
        }
    }

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