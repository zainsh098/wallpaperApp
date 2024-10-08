package com.example.wallpaperapp.fragments.wallpaper

import Photo
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.wallpaperapp.R
import com.example.wallpaperapp.databinding.WallpaperCardItemBinding

class WallpaperAdapter(private val listener: onImageClick) :
    RecyclerView.Adapter<WallpaperAdapter.MyViewHolder>() {

    private var wallpapers: List<Photo> = listOf()

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

    override fun getItemCount(): Int = wallpapers.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val wallpaper = wallpapers[position]
        Glide.with(holder.itemView.context)
            .load(wallpaper.src.portrait)
            .placeholder(R.drawable.home)
            .into(holder.binding.imageViewCategory)

        holder.binding.imageViewCategory.setOnClickListener {
            listener.onPhotoClick(wallpaper.src.original)
        }

    }

    // Method to update wallpapers in adapter
    fun updateWallpapers(newWallpapers: List<Photo>) {
        wallpapers = newWallpapers
        notifyDataSetChanged()
    }

}

interface onImageClick {
    fun onPhotoClick(urlImage: String)
}