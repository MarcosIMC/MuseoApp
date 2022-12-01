package com.example.museoapp.ui.user

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Intent
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.museoapp.model.FireBase.Auth
import com.example.museoapp.model.FireBase.GalleryFireBase
import com.example.museoapp.model.GalleryModel
import com.example.museoapp.model.UserModel

class UserProfileViewModel : ViewModel() {
    // TODO: Implement the ViewModel
    private var authObj = Auth()
    val galleriesFavouritesObjects = MutableLiveData<MutableList<GalleryModel>>()
    val error = MutableLiveData<String?>()
    val galleryFireBase = GalleryFireBase()

    fun log_out(){
        authObj.logOut()
    }

    fun update_profile() {
    }

    fun loadFavourites(gallery: Map<String, Boolean>?) {
        galleryFireBase.getSelected(gallery!!.keys, galleriesFavouritesObjects, error)
        Log.i(TAG, "Fin de la llamada Load, tamaño del galleries: " + galleriesFavouritesObjects.value)

    }
}