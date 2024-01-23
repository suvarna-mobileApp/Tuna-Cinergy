package com.tuna.cinergy.ui.movieinfo.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tuna.cinergy.business.domain.utils.dateFormater
import com.tuna.cinergy.business.domain.utils.weekFormater
import com.tuna.cinergy.databinding.AdapterMovieDatelistBinding
import com.tuna.cinergy.datasource.network.models.movieinfo.MovieInfoResponseClass
import com.tuna.cinergy.datasource.network.models.movieinfo.Sessions
import com.tuna.cinergy.datasource.network.models.movieinfo.ShowTime

class MovieDateRecyclerAdapter (val movieInfo: List<String>, val context: Context,
                                val listener: OnItemClickListener,
                                val movieInfoTime: List<ShowTime>
) : RecyclerView.Adapter<MovieDateRecyclerAdapter.ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = AdapterMovieDatelistBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val currentItem = movieInfo.get(position)
        val sessions = movieInfoTime.get(position).sessions
        if (currentItem != null) {
            holder.bind(currentItem,sessions)
        }
    }

    inner class ItemViewHolder(private val binding: AdapterMovieDatelistBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(currentItem: String,timeList:List<Sessions>) {
            binding.apply {
                itemView.setOnClickListener {
                    listener.onItemClick(currentItem,timeList)
                }
                textDate.setText(dateFormater(currentItem))
                textMon.setText(weekFormater(currentItem))
            }
        }
    }

    override fun getItemCount(): Int {
        return movieInfo.size
    }

    interface OnItemClickListener {
        fun onItemClick(date:String,movieInfoTime:List<Sessions>)
    }
}