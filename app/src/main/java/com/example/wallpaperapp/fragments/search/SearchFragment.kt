package com.example.wallpaperapp.fragments.search

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
import com.example.wallpaperapp.databinding.FragmentSearchBinding
import com.example.wallpaperapp.fragments.wallpaper.WallpaperViewModel
import com.example.wallpaperapp.fragments.wallpaper.onImageClick
import com.example.wallpaperapp.manager.CategoryManager

class SearchFragment : Fragment(), onImageClick {

    private lateinit var binding: FragmentSearchBinding
    private lateinit var adapter: SearchAdapter
    private val viewModel: WallpaperViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

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

            Log.d("Search", "Searching for: $searchQuery")
            viewModel.getWallpapers(searchQuery).observe(viewLifecycleOwner) { wallpapers ->
                if (wallpapers.isNullOrEmpty()) {


                    Toast.makeText(requireContext(), "No wallpapers found", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    binding.searchImage1.visibility = View.GONE
                    binding.textSearch.visibility = View.GONE
                    adapter.updateWallpapers(wallpapers)
                }
            }
        }
    }

    override fun onPhotoClick(urlImage: String) {
        val bundle = Bundle().apply {
            putString("image", urlImage)
            putString("origin", "search") // Indicate that the origin is search
        }
        findNavController().navigate(
            R.id.action_searchFragment_to_fullScreenImageFragment,
            bundle
        )
    }

}
