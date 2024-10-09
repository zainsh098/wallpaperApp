package com.example.wallpaperapp.fragments.fullscreenimage

import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.example.wallpaperapp.R
import com.example.wallpaperapp.databinding.FragmentFullScreenImageBinding
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream


class FullScreenImageFragment : Fragment() {

    private lateinit var binding: FragmentFullScreenImageBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFullScreenImageBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val url = arguments?.getString("image")
        Glide.with(this)
            .load(url)
            .placeholder(R.drawable.place_holderimage)
            .into(binding.fullImage)

        binding.imageShare.setOnClickListener {

            if (url != null) {
                shareImageUrl(url)
            }

        }

        binding.imageDownload.setOnClickListener {

            if (url != null) {
                downloadAndSaveImage(url, "wallpaper_${System.currentTimeMillis()}")
            }

        }

    }

    fun shareImageUrl(url: String) {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Shared Image")
        shareIntent.putExtra(Intent.EXTRA_TEXT, "Check out this awesome image: $url")
        val chooserIntent = Intent.createChooser(shareIntent, "Share Image URL")
        startActivity(chooserIntent)
    }

    fun downloadAndSaveImage(url: String, imageName: String) {
        // Use Glide to download the image
        Glide.with(this)
            .asBitmap()
            .load(url)
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(
                    resource: Bitmap,
                    transition: com.bumptech.glide.request.transition.Transition<in Bitmap>?
                ) {
                    // Save the bitmap to external storage
                    saveImageToGallery(resource, imageName)
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                    // Handle when the image load is cleared (optional)
                }
            })
    }

    private fun saveImageToGallery(bitmap: Bitmap, imageName: String) {
        val fos: OutputStream?
        val folderName = "WallpaperAppImages"

        // Check if the device is running on Android Q or later
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val resolver = requireContext().contentResolver
            val contentValues = ContentValues().apply {
                put(MediaStore.MediaColumns.DISPLAY_NAME, "$imageName.jpg")
                put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
                put(
                    MediaStore.MediaColumns.RELATIVE_PATH,
                    Environment.DIRECTORY_PICTURES + File.separator + folderName
                )
            }

            val imageUri =
                resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
            fos = imageUri?.let { resolver.openOutputStream(it) }
        } else {
            // For Android versions below Q, save to external storage in the Pictures directory
            val imagesDir =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                    .toString() + File.separator + folderName
            val file = File(imagesDir)

            if (!file.exists()) {
                file.mkdirs() // Create the folder if it doesn't exist
            }

            val imageFile = File(file, "$imageName.jpg")
            fos = FileOutputStream(imageFile)

            // Add the image to the gallery so it can be viewed in the Photos app
            val intent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
            intent.data = Uri.fromFile(imageFile)
            requireContext().sendBroadcast(intent)
        }

        // Compress and write the bitmap to the file output stream
        fos?.use {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)
            showToast("Image saved to gallery!")
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()

    }


}