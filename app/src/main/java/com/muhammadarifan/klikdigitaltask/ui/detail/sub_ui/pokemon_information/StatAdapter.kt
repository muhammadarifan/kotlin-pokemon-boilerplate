package com.muhammadarifan.klikdigitaltask.ui.detail.sub_ui.pokemon_information

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.muhammadarifan.klikdigitaltask.R
import com.muhammadarifan.klikdigitaltask.local.model.PokemonStat

class StatAdapter(var data: List<PokemonStat>) :
    RecyclerView.Adapter<StatAdapter.ViewHolder>() {

    class ViewHolder(view: View): RecyclerView.ViewHolder(view)  {
        val tvStatName: TextView = view.findViewById(R.id.tv_stat_name)
        val pbStatValue: ProgressBar = view.findViewById(R.id.pb_stat_value)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_stat_list, parent, false)

        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvStatName.text = data[position].stat_name
        holder.pbStatValue.progress = data[position].stat_value!!.toInt()
    }

    override fun getItemCount(): Int {
        return data.count()
    }
}