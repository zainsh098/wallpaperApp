package com.example.wallpaperapp.fragments.wallpaper

import android.os.Bundle
import android.os.Parcelable
import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
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
import com.example.wallpaperapp.model.Photo
import kotlinx.coroutines.launch

class WallpaperFragment : BaseFragment<FragmentWallpaperBinding>(FragmentWallpaperBinding::inflate),
    onImageClick {

    private val viewModel: WallpaperViewModel by viewModels()
    private lateinit var adapter: WallpaperAdapter
    private var recyclerViewState: Parcelable? = null
    private val sharedViewModel: SharedViewModel by activityViewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = WallpaperAdapter(this)
        val category = CategoryManager.getCategoryName()

        viewModel.recyclerViewState?.let {
            binding.wallapaperRecycler.layoutManager?.onRestoreInstanceState(it)
        }
        binding.apply {
            wallapaperRecycler.layoutManager = GridLayoutManager(context, 2)
            wallapaperRecycler.adapter = adapter
            wallapaperRecycler.itemAnimator = DefaultItemAnimator()
            wallapaperRecycler.addItemDecoration(
                DividerItemDecoration(requireContext(), GridLayoutManager.HORIZONTAL)
            )

            toolbarComponent.textToolbar.text = CategoryManager.getCategoryName()
            binding.toolbarComponent.backArrow.setOnClickListener {
                viewModel.recyclerViewState =
                    binding.wallapaperRecycler.layoutManager?.onSaveInstanceState()
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
            Toast.makeText(requireContext(), "Category not found", Toast.LENGTH_SHORT).show()
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) { error ->
            if (error != null) {
                Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onPause() {
        super.onPause()
        recyclerViewState = binding.wallapaperRecycler.layoutManager?.onSaveInstanceState()
    }

    override fun onResume() {
        super.onResume()
        recyclerViewState?.let {
            binding.wallapaperRecycler.layoutManager?.onRestoreInstanceState(it)
        }
    }


    override fun onPhotoClick(photo: Photo) {

        sharedViewModel.selectedImageUrl = photo.src.original
        sharedViewModel.selectedImageAlt = photo.alt
        findNavController().navigate(R.id.action_wallpaperFragment_to_fullScreenImageFragment)
        activity?.overridePendingTransition(R.anim.card_to_fullscreen, R.anim.card_to_fullscreen)

    }
}
