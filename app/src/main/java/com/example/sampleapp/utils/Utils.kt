package com.example.sampleapp.utils

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.os.Environment
import android.text.format.DateFormat
import android.view.View
import android.view.inputmethod.InputMethodManager
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.*

object Utils {

    fun hideSoftKeyboard(activity: Activity, view: View) {
        val inputManager =
            activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun showSoftKeyboard(activity: Activity, view: View) {
        // val view = activity.currentFocus
        val methodManager =
            activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        try {
            methodManager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
        } catch (e: Exception) {
        }

    }

    fun takeScreenShot(view: View) : File {
        var imageFile: File? = null
        //This is used to provide file name with Date a format

        val currentTime = Calendar.getInstance().timeInMillis.toString()
        //It will make sure to store file to given below Directory and If the file Directory dosen't exist then it will create it.
        try {
            val mainDir = File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "MyCash"
            )
            if (!mainDir.exists()) {
                mainDir.mkdir()
            }

            //Providing file name along with Bitmap to capture screenview
            val path = "$mainDir/MyCash-$currentTime.jpeg"
            path.replace(":", ".")
            view.isDrawingCacheEnabled = true
            val bitmap: Bitmap = Bitmap.createBitmap(view.drawingCache)
            view.isDrawingCacheEnabled = false


//This logic is used to save file at given location with the given filename and compress the Image Quality.
            imageFile = File(path)
            val fileOutputStream = FileOutputStream(imageFile)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream)
            fileOutputStream.flush()
            fileOutputStream.close()
//Create New Method to take ScreenShot with the imageFile.
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return imageFile!!
    }

}