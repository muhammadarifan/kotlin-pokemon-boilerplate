package com.muhammadarifan.pokemonboilerplate.ui.detail.sub_ui.pokemon_evolution

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.muhammadarifan.pokemonboilerplate.R
import com.muhammadarifan.pokemonboilerplate.local.model.PokemonEvolution
import com.muhammadarifan.pokemonboilerplate.util.Variable

class EvolutionAdapter(var data: List<PokemonEvolution>) :
    RecyclerView.Adapter<EvolutionAdapter.ViewHolder>() {

    class ViewHolder(view: View): RecyclerView.ViewHolder(view)  {
        val tvName: TextView = view.findViewById(R.id.tvName)
        val ivOfficialArtwork: ImageView = view.findViewById(R.id.ivOfficialArtwork)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_pokemon_list, parent, false)

        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvName.text = data[position].name
        Glide.with(holder.itemView)
            .load(Variable.POKEMON_OFFICIAL_ART_URL + data[position].evolution_pokemon_id + ".png")
            .centerCrop()
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(holder.ivOfficialArtwork)
    }

    override fun getItemCount(): Int {
        return data.count()
    }
}