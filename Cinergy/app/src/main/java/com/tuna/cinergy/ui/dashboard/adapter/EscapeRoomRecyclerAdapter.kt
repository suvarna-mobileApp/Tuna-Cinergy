package com.tuna.cinergy.ui.dashboard.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tuna.cinergy.databinding.RecyclerEscapeRoomAdapterBinding
import com.tuna.cinergy.datasource.network.models.escaperoom.EscapeRoom
import com.tuna.cinergy.datasource.network.models.escaperoom.EscapeRoomResponseClass

class EscapeRoomRecyclerAdapter (val escapeRoom: EscapeRoomResponseClass, val context: Context,private val listener: OnItemClickListener
) : RecyclerView.Adapter<EscapeRoomRecyclerAdapter.ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = RecyclerEscapeRoomAdapterBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val currentItem = escapeRoom.escape_rooms_movies.get(position)
        if (currentItem != null) {
            holder.bind(currentItem,position,escapeRoom)
        }
    }

    inner class ItemViewHolder(private val binding: RecyclerEscapeRoomAdapterBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(currentItem: EscapeRoom, position: Int,escapeRoom:EscapeRoomResponseClass) {
            binding.apply {
                itemView.setOnClickListener {
                    listener.onItemClick(currentItem.Title,currentItem.RunTime,currentItem.Synopsis,escapeRoom.er_tickets,currentItem.image_url,currentItem.ID)
                }

                Glide.with(binding.root.context)
                    .load(currentItem.image_url)
                    .into(binding.imgPoster)

                txtTitle.text = currentItem.Title
            }
        }
    }

    override fun getItemCount(): Int {
        return escapeRoom.escape_rooms_movies.size
    }

    interface OnItemClickListener {
        fun onItemClick(title:String,runTime:String,synopsis:String,ticket:String,imageUrl:String,movieId: String)
    }
}