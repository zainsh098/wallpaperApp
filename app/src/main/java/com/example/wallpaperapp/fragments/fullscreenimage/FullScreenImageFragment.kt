package com.example.wallpaperapp.fragments.fullscreenimage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.wallpaperapp.R
import com.example.wallpaperapp.databinding.FragmentFullScreenImageBinding

class FullScreenImageFragment : Fragment() {

    private lateinit var binding: FragmentFullScreenImageBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFullScreenImageBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val url = arguments?.getString("image")
        Glide.with(this)
            .load(url)
            .placeholder(R.drawable.home)
            .into(binding.fullImage)
    }


}