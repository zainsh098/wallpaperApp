package com.example.wallpaperapp.fragments.wallpaper

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.wallpaperapp.R
import com.example.wallpaperapp.databinding.FragmentWallpaperBinding

class WallpaperFragment : Fragment(), onImageClick {

    private lateinit var binding: FragmentWallpaperBinding
    private val viewModel: WallpaperViewModel by viewModels()
    private lateinit var adapter: WallpaperAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWallpaperBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = WallpaperAdapter(this)
        val category = arguments?.getString("name")

        binding.apply {
            wallapaperRecycler.layoutManager = GridLayoutManager(context, 2)
            wallapaperRecycler.adapter = adapter
            toolbarComponent.textToolbar.text = category.toString()

            toolbarComponent.backArrow.setOnClickListener {
                findNavController().navigate(R.id.action_wallpaperFragment_to_homeFragment)

            }
        }

        Log.d("nammsmd", category.toString())
        viewModel.getWallpapers(category!!).observe(viewLifecycleOwner) { wallpapers ->
            adapter.updateWallpapers(wallpapers)
        }
        viewModel.errorMessage.observe(viewLifecycleOwner) { error ->
            if (error != null) {
                Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onPhotoClick(urlImage: String) {
        val bundle = Bundle().apply {
            putString("image", urlImage)
        }
        findNavController().navigate(
            R.id.action_wallpaperFragment_to_fullScreenImageFragment,
            bundle
        )

    }
}
