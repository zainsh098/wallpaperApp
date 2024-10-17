package com.example.wallpaperapp.fragments.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.wallpaperapp.databinding.HomeCardItemBinding
import com.example.wallpaperapp.model.Category

class HomeAdapter(
    private val categories: List<Category>,
    private val listener: onCategoryItemClick
) : RecyclerView.Adapter<HomeAdapter.MyViewHolder>() {

   inner class MyViewHolder(val binding: HomeCardItemBinding) : RecyclerView.ViewHolder(binding.root) {
       fun bind(categories: Category) {
            binding.txtCategory.text = categories.name
            binding.imageViewCategory.setImageResource(categories.image)

        }
    }

    val Context.layoutInflater:LayoutInflater
        get() = LayoutInflater.from(this)

    val View.layoutInflater:LayoutInflater
        get() = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            HomeCardItemBinding.inflate(parent.layoutInflater, parent, false)
        )
    }

    override fun getItemCount()= categories.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val category = categories[position]
        holder.bind(categories[position])
        holder.binding.imageViewCategory.setOnClickListener {
            listener.onClick(category.name)
        }
    }
}

interface onCategoryItemClick {
    fun onClick(name: String)
}