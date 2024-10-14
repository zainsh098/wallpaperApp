package com.example.wallpaperapp.fragments.fullscreenimage

import android.app.AlertDialog
import android.app.WallpaperManager
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.example.wallpaperapp.R
import com.example.wallpaperapp.basefragment.BaseFragment
import com.example.wallpaperapp.databinding.DialogSetWallpaperBinding
import com.example.wallpaperapp.databinding.FragmentFullScreenImageBinding

class FullScreenImageFragment :
    BaseFragment<FragmentFullScreenImageBinding>(FragmentFullScreenImageBinding::inflate) {

    private val viewModel: FullScreenImageViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val url = arguments?.getString(getString(R.string.image))

        Glide.with(this)
            .load(url)
            .placeholder(R.drawable.place_holderimage)
            .into(binding.fullImage)

        binding.backArrow.setOnClickListener {
            val origin = arguments?.getString(getString(R.string.origin))
            if (origin == getString(R.string.search)) {
                findNavController().navigate(R.id.action_fullScreenImageFragment_to_searchFragment)
            } else {
                findNavController().navigate(R.id.action_fullScreenImageFragment_to_wallpaperFragment)
            }
        }
        binding.apply {
            imageShare.setOnClickListener {
                url?.let { shareImageUrl(it) }
            }
            imageDownload.setOnClickListener {
                url?.let {
                    viewModel.downloadAndSaveImage(
                        requireContext(),
                        it,
                        "wallpaper_${System.currentTimeMillis()}"
                    )
                }
            }
            imageSetWallpaper.setOnClickListener {
                url?.let { showSetWallpaperDialog(it) }
            }
        }
    }

    private fun shareImageUrl(url: String) {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.shared_image))
        shareIntent.putExtra(
            Intent.EXTRA_TEXT,
            getString(R.string.check_out_this_awesome_image, url)
        )
        val chooserIntent = Intent.createChooser(shareIntent, getString(R.string.share_image_url))
        startActivity(chooserIntent)
    }

    private fun showSetWallpaperDialog(url: String) {
        val dialogBinding = DialogSetWallpaperBinding.inflate(layoutInflater)
        val radioGroup = dialogBinding.wallpaperRadioGroup

        val dialog = AlertDialog.Builder(requireContext(), R.style.CustomAlertDialogTheme)
            .setView(dialogBinding.root)
            .setPositiveButton(getString(R.string.set)) { dialogInterface, _ ->

                val selectedId = radioGroup.checkedRadioButtonId
                when (selectedId) {
                    R.id.radioHome -> downloadImageForWallpaper(url, WallpaperManager.FLAG_SYSTEM)
                    R.id.radioLock -> downloadImageForWallpaper(url, WallpaperManager.FLAG_LOCK)
                    R.id.radioBoth -> downloadImageForWallpaper(
                        url,
                        WallpaperManager.FLAG_SYSTEM or WallpaperManager.FLAG_LOCK
                    )
                }
                dialogInterface.dismiss()
                binding.blurredBackground.visibility = View.VISIBLE
                binding.progressBar.visibility = View.VISIBLE

            }
            .setNegativeButton(getString(R.string.cancel), null)
            .create()
        dialog.show()
    }

    private fun downloadImageForWallpaper(url: String, wallpaperType: Int) {
        Glide.with(this)
            .asBitmap()
            .load(url)
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(
                    resource: Bitmap,
                    transition: com.bumptech.glide.request.transition.Transition<in Bitmap>?
                ) {
                    viewModel.setWallpaper(requireContext(), resource, wallpaperType)
                    binding.blurredBackground.visibility = View.GONE
                    binding.progressBar.visibility = View.GONE

                }

                override fun onLoadCleared(placeholder: Drawable?) {}
            })
    }
}
