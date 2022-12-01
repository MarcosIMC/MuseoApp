package com.example.museoapp.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.museoapp.model.FireBase.UserFireBase
import com.example.museoapp.model.GalleryModelSerializable

class DetailItemViewModel : ViewModel() {
    val favouritesId = MutableLiveData<MutableList<String>>()
    val userFireBase = UserFireBase()
    val error = MutableLiveData<String?>()
    val mensaje = MutableLiveData<String?>()

    fun getFavourites() {
        userFireBase.getFavourites(favouritesId, error)
    }

    fun addFavourite(id_item: GalleryModelSerializable?) {
        userFireBase.addFavourites(id_item?.key, mensaje)
    }

    fun removeFavourite(id_item: GalleryModelSerializable?) {
        userFireBase.removeFavourite(id_item?.key, mensaje)
    }
}