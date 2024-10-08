package com.example.wallpaperapp.fragments.search

import Photo
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.wallpaperapp.R
import com.example.wallpaperapp.databinding.WallpaperCardItemBinding
import com.example.wallpaperapp.fragments.wallpaper.onImageClick

class SearchAdapter(private val listener: onImageClick) : RecyclerView.Adapter<SearchAdapter.MyViewHolder>() {

    private var wallpapers: List<Photo> = emptyList()

    inner class MyViewHolder( val binding: WallpaperCardItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchAdapter.MyViewHolder {
        val binding =
            WallpaperCardItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchAdapter.MyViewHolder, position: Int) {

        val wallpaper = wallpapers[position]
        Glide.with(holder.itemView.context)
            .load(wallpaper.src.portrait)
            .placeholder(R.drawable.home)
            .into(holder.binding.imageViewCategory)

        holder.binding.imageViewCategory.setOnClickListener {
            listener.onPhotoClick(wallpaper.src.portrait)
        }

    }
    override fun getItemCount(): Int {
        return wallpapers.size
    }

    fun updateWallpapers(newWallpapers: List<Photo>) {
        wallpapers = newWallpapers
        notifyDataSetChanged()
    }
}
