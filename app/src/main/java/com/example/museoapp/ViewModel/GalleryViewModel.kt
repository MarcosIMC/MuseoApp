package com.example.museoapp.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.museoapp.model.FireBase.GalleryFireBase
import com.example.museoapp.model.GalleryModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class GalleryViewModel : ViewModel() {
    val galleriesObjects = MutableLiveData<MutableList<GalleryModel>>()
    val galleriesImage = MutableLiveData<ArrayList<String>>()
    val error = MutableLiveData<String?>()
    val galleryFireBase = GalleryFireBase()
    val isLoading = MutableLiveData<Boolean>()

    fun getAllElements(){
        //val activity = MainActivity()
        viewModelScope.launch(Dispatchers.IO) {
            delay(1000)
            isLoading.postValue(true)
            galleryFireBase.getAll(galleriesObjects, error)
        }
    }

    fun getMainImages(){
        viewModelScope.launch(Dispatchers.IO) {
            galleryFireBase.getMainImages(galleriesImage, error)
        }
    }
}