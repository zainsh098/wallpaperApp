package com.example.wallpaperapp.fragments.search

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.wallpaperapp.R
import com.example.wallpaperapp.base.BaseFragment
import com.example.wallpaperapp.databinding.FragmentSearchBinding
import com.example.wallpaperapp.fragments.wallpaper.SharedViewModel
import com.example.wallpaperapp.fragments.wallpaper.onImageClick
import com.example.wallpaperapp.manager.CategoryManager
import com.example.wallpaperapp.model.Photo
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SearchFragment : BaseFragment<FragmentSearchBinding>(FragmentSearchBinding::inflate),
    onImageClick {

    private lateinit var adapter: SearchAdapter
    private val viewModel: SearchViewModel by viewModels()
    private val sharedViewModel: SharedViewModel by activityViewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = SearchAdapter(this)
        binding.apply {
            binding.textSearch.text = getString(R.string.start_searching)
            wallapaperRecycler.layoutManager = GridLayoutManager(context, 2)
            wallapaperRecycler.adapter = adapter
            searchImage1.visibility = View.VISIBLE
            textSearch.visibility = View.VISIBLE
            searchComponent.backArrow.setOnClickListener {
                findNavController().navigate(R.id.action_searchFragment_to_homeFragment)

            }
        }

//        viewModel.isLoading.observe(viewLifecycleOwner){
//                isLoading->
//            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
//
//
//        }
        binding.searchComponent.searchImage.setOnClickListener {
            val searchQuery = binding.searchComponent.searchTextField.text.toString().trim()
            CategoryManager.setCategoryName(searchQuery)
            if (searchQuery.isEmpty()) {

                Toast.makeText(requireContext(), "Please enter a search term", Toast.LENGTH_SHORT)
                    .show()
            }
            lifecycleScope.launch {
                viewModel.getPagedWallpapers(searchQuery).collectLatest { pagingData ->
                    Log.d("SearchFragment", "PagingData received: ${pagingData}")
                    binding.searchImage1.visibility = View.GONE
                    binding.textSearch.visibility = View.GONE
                    adapter.submitData(pagingData)
                }
            }

        }
    }

    override fun onPhotoClick(photo: Photo) {
        sharedViewModel.selectedImageUrl = photo.url
        sharedViewModel.selectedImageAlt = photo.alt
        val bundle = Bundle().apply {
            putString("origin", "search") // Indicate that the origin is search
        }

        findNavController().navigate(
            R.id.action_searchFragment_to_fullScreenImageFragment,
            bundle
        )
    }

}
