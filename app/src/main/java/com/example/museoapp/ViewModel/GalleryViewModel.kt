package com.example.museoapp.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.museoapp.model.GalleryModel

class GalleryViewModel : ViewModel() {
    val galleryModel = MutableLiveData<GalleryModel>()

}