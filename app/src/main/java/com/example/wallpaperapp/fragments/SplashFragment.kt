package com.example.wallpaperapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.wallpaperapp.R
import com.example.wallpaperapp.databinding.FragmentSplashBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashFragment : Fragment() {


    private lateinit var binding: FragmentSplashBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSplashBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fadeIn = AnimationUtils.loadAnimation(requireContext(), R.anim.fade_in)
        val slideUp = AnimationUtils.loadAnimation(requireContext(), R.anim.slide_up)

        binding.apply {
            txtSplashWelcome.visibility = View.GONE
            splashLayout.visibility = View.GONE
            buttonStart.visibility = View.GONE
        }

        lifecycleScope.launch {
            delay(2000)

            binding.apply {
                txtSplashWelcome.visibility = View.VISIBLE
                splashLayout.visibility = View.VISIBLE
                buttonStart.visibility = View.VISIBLE
                buttonStart.startAnimation(slideUp)
                txtSplashWelcome.startAnimation(slideUp)
                splashLayout.startAnimation(fadeIn)
            }
        }

        binding.buttonStart.setOnClickListener {
            findNavController().navigate(R.id.action_splashFragment_to_homeFragment)
        }

    }

}
