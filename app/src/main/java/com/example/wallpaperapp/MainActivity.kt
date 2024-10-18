package com.example.wallpaperapp

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.NavHostFragment
import com.example.wallpaperapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        window.statusBarColor = ContextCompat.getColor(applicationContext, R.color.lightBlack)

//        val navHostFragment =
//            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
//        val navController = navHostFragment.navController
        val navController =
            binding.fragmentContainerView.getFragment<NavHostFragment>().navController


        binding.bottomNavView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.homeFragment -> {
                    navController.navigate(R.id.homeFragment)
                    true
                }

                R.id.searchFragment -> {
                    navController.navigate(R.id.searchFragment)
                    true
                }

                R.id.favouriteFragment -> {
                    navController.navigate(R.id.favouriteFragment)
                    true
                }

                else -> false
            }
        }
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.splashFragment -> hideBottomNav()
                R.id.homeFragment -> showBottomNav()
                R.id.searchFragment -> hideBottomNav()
                R.id.wallpaperFragment -> hideBottomNav()
                R.id.fullScreenImageFragment -> hideBottomNav()
                else -> showBottomNav()
            }
        }

    }

    private fun showBottomNav() {
        binding.bottomNavView.visibility = View.VISIBLE
    }

    private fun hideBottomNav() {
        binding.bottomNavView.visibility = View.GONE
    }

//    private fun showSnackBar(message: String) {
//        val snack = Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG)
//        snack.setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_FADE)
//        snack.setAction("dismiss") {
//            snack.dismiss()
//            snack.setAnimationMode(R.anim.slide_up)
//        }
//        snack.show()
//    }
}
