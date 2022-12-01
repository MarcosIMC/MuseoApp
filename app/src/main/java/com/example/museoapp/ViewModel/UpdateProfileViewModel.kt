package com.example.museoapp.ViewModel

import android.graphics.Bitmap
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.museoapp.model.FireBase.Auth
import com.example.museoapp.model.GalleryModel
import com.example.museoapp.ui.UpdateForm.UpdateProfileFormActivity
import com.google.firebase.auth.FirebaseUser

class UpdateProfileViewModel : ViewModel(){
    private var authObj = Auth()
    val userFirebase = MutableLiveData<FirebaseUser?>()
    val error = MutableLiveData<String?>()

    fun updateUser(
        image: Bitmap?,
        email: String,
        name: String,
        surname: String,
        phone: String,
        gallery: MutableMap<String, Boolean>,
        password: String
    ) {
        authObj.updateUserDataWithPass(email, password, name,
        surname, phone.toLong(), gallery, image, UpdateProfileFormActivity(), userFirebase, error)
    }
}