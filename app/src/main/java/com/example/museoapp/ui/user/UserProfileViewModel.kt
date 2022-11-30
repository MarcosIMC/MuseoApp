package com.example.museoapp.ui.user

import android.app.Activity
import android.content.Intent
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.museoapp.model.FireBase.Auth
import com.example.museoapp.model.UserModel

class UserProfileViewModel : ViewModel() {
    // TODO: Implement the ViewModel
    private var authObj = Auth()

    fun log_out(){
        authObj.logOut()
    }

    fun update_profile() {

    }

    fun launchListFavourites(
        activity: Activity?
    ) {
        val intent = Intent(activity, ListFavouritesActivity::class.java)
        activity!!.startActivity(intent)
    }
}