package com.example.museoapp.ui.admin

import android.graphics.Bitmap
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.museoapp.model.FireBase.Auth
import com.example.museoapp.model.FireBase.GalleryFireBase

class CreateGalleryViewModel: ViewModel() {
    private var galleryFireBase = GalleryFireBase()
    val message = MutableLiveData<String?>()
    val error = MutableLiveData<String?>()

    fun createGallery(
        image: Bitmap?,
        title: String,
        sort_description: String,
        long_description: String
    ){
        galleryFireBase.createGallery(image, title, sort_description, long_description, message, error)
    }
}