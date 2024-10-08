package com.example.wallpaperapp.fragments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.wallpaperapp.R
import com.example.wallpaperapp.databinding.FragmentHomeBinding
import com.example.wallpaperapp.manager.CategoryManager

class HomeFragment : Fragment(), onCategoryItemClick {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeAdapter: HomeAdapter
    private val homeViewModel: HomeViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeAdapter = HomeAdapter(homeViewModel.categories, this)
        binding.toolbarComponent.textToolbar.text = getString(R.string.wallify)
        binding.homeRecycler.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.homeRecycler.adapter = homeAdapter
        binding.toolbarComponent.backArrow.visibility = View.GONE
    }

    override fun onClick(name: String) {
//        val bundle = Bundle().apply {
//            putString("name", name)
//        }
        findNavController().navigate(R.id.action_homeFragment_to_wallpaperFragment)
    CategoryManager.setCategoryName(name)
    }
}