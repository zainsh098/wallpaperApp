package com.example.wallpaperapp.fragments.fullscreenimage

import android.app.WallpaperManager
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

class FullScreenImageViewModel : ViewModel() {

    fun downloadAndSaveImage(context: Context, url: String, imageName: String) {
        // Use Glide to download the image
        // GLide utils code  move here
        Glide.with(context)
            .asBitmap()
            .load(url)
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(
                    resource: Bitmap,
                    transition: Transition<in Bitmap>?
                ) {
                    saveImageToGallery(context, resource, imageName)
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                    // Handle when the image load is cleared (optional)
                }
            })
    }

    private fun saveImageToGallery(context: Context, bitmap: Bitmap, imageName: String) {
        val fos: OutputStream?
        val folderName = "WallpaperAppImages"

        // Check if the device is running on Android Q or later
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val resolver = context.contentResolver
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
            context.sendBroadcast(intent)
        }

        // Compress and write the bitmap to the file output stream
        fos?.use {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)
            Toast.makeText(context, "Image saved to gallery!", Toast.LENGTH_SHORT).show()
        }
    }

    fun setWallpaper(context: Context, bitmap: Bitmap, wallpaperType: Int) {
        try {
            val wallpaperManager = WallpaperManager.getInstance(context)

            when (wallpaperType) {
                WallpaperManager.FLAG_SYSTEM -> {
                    wallpaperManager.setBitmap(bitmap, null, true, WallpaperManager.FLAG_SYSTEM)
                    Toast.makeText(context, "Home screen wallpaper set!", Toast.LENGTH_SHORT).show()
                }

                WallpaperManager.FLAG_LOCK -> {
                    wallpaperManager.setBitmap(bitmap, null, true, WallpaperManager.FLAG_LOCK)
                    Toast.makeText(context, "Lock screen wallpaper set!", Toast.LENGTH_SHORT).show()
                }

                else -> {
                    wallpaperManager.setBitmap(
                        bitmap,
                        null,
                        true,
                        WallpaperManager.FLAG_SYSTEM or WallpaperManager.FLAG_LOCK
                    )
                    Toast.makeText(
                        context,
                        "Home and lock screen wallpaper set!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(context, "Failed to set wallpaper!", Toast.LENGTH_SHORT).show()
        }
    }
}
