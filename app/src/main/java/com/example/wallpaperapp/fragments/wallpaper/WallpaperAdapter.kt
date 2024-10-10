package com.example.wallpaperapp.fragments.wallpaper

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.wallpaperapp.R
import com.example.wallpaperapp.databinding.WallpaperCardItemBinding
import com.example.wallpaperapp.model.Photo

class WallpaperAdapter(private val listener: onImageClick) :
    PagingDataAdapter<Photo, WallpaperAdapter.MyViewHolder>(WallpaperDiffCallback()) {

    class MyViewHolder(val binding: WallpaperCardItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = WallpaperCardItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val wallpaper = getItem(position)
        if (wallpaper != null) {
            Glide.with(holder.itemView.context)
                .load(wallpaper.src.portrait)
                .placeholder(R.drawable.place_holderimage)
                .into(holder.binding.imageViewCategory)

            holder.binding.imageViewCategory.setOnClickListener {
                listener.onPhotoClick(wallpaper.src.original)
            }
        }
    }

    // DiffUtil to efficiently update items
    class WallpaperDiffCallback : DiffUtil.ItemCallback<Photo>() {
        override fun areItemsTheSame(oldItem: Photo, newItem: Photo): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean {
            return oldItem == newItem
        }
    }
}

interface onImageClick {
    fun onPhotoClick(urlImage: String)
}