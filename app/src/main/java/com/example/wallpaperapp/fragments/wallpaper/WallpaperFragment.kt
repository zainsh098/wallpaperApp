package com.example.wallpaperapp.fragments.wallpaper

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import com.example.wallpaperapp.R
import com.example.wallpaperapp.base.BaseFragment
import com.example.wallpaperapp.databinding.FragmentWallpaperBinding
import com.example.wallpaperapp.manager.CategoryManager
import kotlinx.coroutines.launch

class WallpaperFragment :BaseFragment<FragmentWallpaperBinding>(FragmentWallpaperBinding::inflate), onImageClick {

    private val viewModel: WallpaperViewModel by viewModels()
    private lateinit var adapter: WallpaperAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = WallpaperAdapter(this)
        val category = CategoryManager.getCategoryName()

//        val category = arguments?.getString("name")
//        CategoryManager.setCategoryName(category)

        binding.apply {
            wallapaperRecycler.layoutManager = GridLayoutManager(context, 2)
            wallapaperRecycler.adapter = adapter
            wallapaperRecycler.itemAnimator = DefaultItemAnimator()
            wallapaperRecycler.addItemDecoration(
                DividerItemDecoration(requireContext(), GridLayoutManager.HORIZONTAL)
            )
            toolbarComponent.textToolbar.text = CategoryManager.getCategoryName()

            toolbarComponent.backArrow.setOnClickListener {
                findNavController().navigate(R.id.action_wallpaperFragment_to_homeFragment)
            }
        }

        if (category != null) {
            lifecycleScope.launch {
                viewModel.getPagedWallpapers(category).collect { pagingData ->
                    adapter.submitData(pagingData)
                }
            }
        } else {
            // Handle the null case, e.g., show an error message or navigate back
            Toast.makeText(requireContext(), "Category not found", Toast.LENGTH_SHORT).show()
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) { error ->
            if (error != null) {
                Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
            }


        }
    }

    override fun onPhotoClick(urlImage: String,alt:String) {
        val bundle = Bundle().apply {
            putString("image", urlImage)
            putString("alt",alt)
        }
        findNavController().navigate(
            R.id.action_wallpaperFragment_to_fullScreenImageFragment,
            bundle
        )
    }
}
