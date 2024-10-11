package com.example.wallpaperapp.fragments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.wallpaperapp.R
import com.example.wallpaperapp.databinding.FragmentHomeBinding
import com.example.wallpaperapp.manager.CategoryManager
import com.example.wallpaperapp.repository.ConnectivityRepository
import com.example.wallpaperapp.viewmodel.NetworkViewModel
import com.google.android.material.snackbar.Snackbar

class HomeFragment : Fragment(), onCategoryItemClick {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeAdapter: HomeAdapter
    private val homeViewModel: HomeViewModel by viewModels()
    private lateinit var networkViewModel: NetworkViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val connectivityRepository = ConnectivityRepository(requireContext())

        networkViewModel = NetworkViewModel(connectivityRepository)

        homeAdapter = HomeAdapter(homeViewModel.categories, this)
        binding.toolbarComponent.textToolbar.text = getString(R.string.wallify)
        binding.homeRecycler.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.homeRecycler.adapter = homeAdapter
        binding.toolbarComponent.backArrow.visibility = View.GONE

        networkViewModel.isOnline.observe(viewLifecycleOwner) { isOnline ->
            if (isOnline) {
                Snackbar.make(binding.root,"Network is Online",Snackbar.LENGTH_LONG).show()
//                showToast("Network is Back")
            } else {
                Snackbar.make(binding.root,"Network is Offline",Snackbar.LENGTH_LONG).show()
            }
        }
    }

    override fun onClick(name: String) {
        findNavController().navigate(R.id.action_homeFragment_to_wallpaperFragment)
        CategoryManager.setCategoryName(name)
    }

    private fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG,).show()
    }
}
