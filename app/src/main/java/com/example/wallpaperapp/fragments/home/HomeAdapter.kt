package com.example.wallpaperapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.wallpaperapp.databinding.HomeCardItemBinding
import com.example.wallpaperapp.model.Category

class HomeAdapter(
    private val categories: List<Category>,
    private val listener: onCategoryItemClick
) : RecyclerView.Adapter<HomeAdapter.MyViewHolder>() {

    class MyViewHolder(val binding: HomeCardItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            HomeCardItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return categories.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val category = categories[position]
        holder.binding.txtCategory.text = category.name
        holder.binding.imageViewCategory.setOnClickListener {
            listener.onClick(category.name)
        }


        // Load the image using Glide
//        Glide.with(holder.itemView.context)
//            .load(category.imageUrl)
//            .into(holder.binding.imageViewCategory)

    }

    interface onCategoryItemClick {
        fun onClick(name: String)


    }
}
