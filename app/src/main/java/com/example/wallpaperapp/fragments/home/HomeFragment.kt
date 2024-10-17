package com.example.wallpaperapp.fragments.home

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.wallpaperapp.R
import com.example.wallpaperapp.base.BaseFragment
import com.example.wallpaperapp.databinding.FragmentHomeBinding
import com.example.wallpaperapp.manager.CategoryManager
import com.example.wallpaperapp.repository.ConnectivityRepository
import com.example.wallpaperapp.viewmodel.NetworkViewModel
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar

class HomeFragment() : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate), onCategoryItemClick {

    private lateinit var homeAdapter: HomeAdapter
    private val homeViewModel: HomeViewModel by viewModels()
    private lateinit var networkViewModel: NetworkViewModel
    private var previousNetworkState: Boolean? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val connectivityRepository = ConnectivityRepository(requireContext())


        // binding use krni ha idhr

        binding.apply {
           toolbarComponent.textToolbar.text = getString(R.string.wallify)
           homeRecycler.layoutManager = GridLayoutManager(requireContext(), 2)
           homeRecycler.adapter = homeAdapter
           toolbarComponent.backArrow.visibility = View.GONE
        }

        networkViewModel = NetworkViewModel(connectivityRepository)
        homeAdapter = HomeAdapter(homeViewModel.categories, this)

        networkViewModel.isOnline.observe(viewLifecycleOwner) { isOnline ->
            if (previousNetworkState != null && previousNetworkState != isOnline) {
                if (isOnline) {
                    showSnackBar(getString(R.string.network_is_online))
                } else {
                    showSnackBar(getString(R.string.check_your_internet_network_is_offline))
                }
            }
            previousNetworkState = isOnline
        }
    }


    override fun onClick(name: String) {
        findNavController().navigate(R.id.action_homeFragment_to_wallpaperFragment)
        CategoryManager.setCategoryName(name)
    }

    private fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    private fun showSnackBar(message: String) {
        val snack = Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG)
        snack.setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_FADE)
        snack.setAction("dismiss") {
            snack.dismiss()
            snack.setAnimationMode(R.anim.slide_up)
        }
        snack.show()
    }
}
