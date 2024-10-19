package com.example.wallpaperapp.fragments.favourite

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.example.wallpaperapp.R
import com.example.wallpaperapp.base.BaseFragment
import com.example.wallpaperapp.databinding.FragmentFavouriteBinding
import com.example.wallpaperapp.repository.FavouriteRepoImplementation
import com.example.wallpaperapp.room.FavouriteDatabase

class FavouriteFragment :
    BaseFragment<FragmentFavouriteBinding>(FragmentFavouriteBinding::inflate) {

    private lateinit var favouriteViewModel: FavouriteViewModel
    private val favouriteAdapter: FavouriteAdapter = FavouriteAdapter()

    private val favouriteDao by lazy {
        FavouriteDatabase.getDatabase(requireContext()).favouriteDao()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        favouriteViewModel = FavouriteViewModel(FavouriteRepoImplementation(favouriteDao))

        binding.apply {
            wallapaperRecycler.adapter = favouriteAdapter
            wallapaperRecycler.layoutManager = GridLayoutManager(context, 2)
        }

        favouriteViewModel.getAllFavourites().observe(viewLifecycleOwner) { favourites ->
            favouriteAdapter.submitList(favourites)
        }


        binding.toolbarComponent.textToolbar.text = getString(R.string.favourite)
    }
}
