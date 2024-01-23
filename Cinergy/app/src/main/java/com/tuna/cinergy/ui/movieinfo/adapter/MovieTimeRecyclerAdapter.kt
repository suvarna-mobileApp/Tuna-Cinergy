package com.tuna.cinergy.ui.movieinfo.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tuna.cinergy.databinding.AdapterMovieTimelistBinding
import com.tuna.cinergy.datasource.network.models.movieinfo.Sessions

class MovieTimeRecyclerAdapter (val context: Context,
                                val movieInfoTime: List<Sessions>
) : RecyclerView.Adapter<MovieTimeRecyclerAdapter.ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = AdapterMovieTimelistBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val currentItem = movieInfoTime.get(position)
        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }

    inner class ItemViewHolder(private val binding: AdapterMovieTimelistBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(currentItem: Sessions) {
            binding.apply {
                textTime.text = currentItem.Showtime
            }
        }
    }

    override fun getItemCount(): Int {
        return movieInfoTime.size
    }
}