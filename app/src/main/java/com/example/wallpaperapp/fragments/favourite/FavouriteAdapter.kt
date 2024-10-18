package com.example.wallpaperapp.fragments.favourite

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.wallpaperapp.R
import com.example.wallpaperapp.databinding.FavouriteWallpaperItemBinding
import com.example.wallpaperapp.ext.layoutInflater
import com.example.wallpaperapp.model.Favourite

class FavouriteAdapter : RecyclerView.Adapter<FavouriteAdapter.FavouriteViewHolder>() {

    private val favouriteWallpaper: ArrayList<Favourite> = arrayListOf()

    inner class FavouriteViewHolder(val binding: FavouriteWallpaperItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(favourite: Favourite) {
            Glide.with(binding.root)
                .load(favourite.imageUrl)
                .placeholder(R.drawable.place_holderimage)
                .into(binding.imageWallpaper)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteViewHolder {
        return FavouriteViewHolder(
            FavouriteWallpaperItemBinding.inflate(
                parent.layoutInflater,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: FavouriteViewHolder, position: Int) {
        holder.bind(favouriteWallpaper[position])
    }

    override fun getItemCount(): Int = favouriteWallpaper.size

    fun submitList(list: List<Favourite>) {
        favouriteWallpaper.clear()
        favouriteWallpaper.addAll(list)
        notifyDataSetChanged()
    }
}
