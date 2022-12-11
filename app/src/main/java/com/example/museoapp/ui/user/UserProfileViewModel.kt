package com.example.museoapp.ui.user

import android.content.ContentValues.TAG
import android.util.Log
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bumptech.glide.Glide
import com.example.museoapp.R
import com.example.museoapp.databinding.FragmentUserProfileBinding
import com.example.museoapp.model.FireBase.Auth
import com.example.museoapp.model.FireBase.GalleryFireBase
import com.example.museoapp.model.GalleryModel
import com.example.museoapp.model.UserModel
import com.google.firebase.auth.FirebaseUser

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

    fun setDatas(
        binding: FragmentUserProfileBinding,
        activity: FragmentActivity?,
        userModel: UserModel,
        currentUser: FirebaseUser
    ) {
        Glide.with(activity!!).load(userModel.image).centerCrop().placeholder(R.drawable.ic_baseline_broken_image_24).error(
            com.google.android.material.R.drawable.mtrl_ic_error).timeout(500).override(204,190).into(binding.imageViewProfile)
        var fullName = currentUser.displayName + " " + userModel.surname
        binding.textViewName.text = fullName
        binding.textViewEmail.text = currentUser.email
        binding.textViewTlf.text = userModel.tlf.toString()
    }
}