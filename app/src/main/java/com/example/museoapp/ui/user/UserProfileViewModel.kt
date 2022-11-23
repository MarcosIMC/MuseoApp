package com.example.museoapp.ui.user

import androidx.lifecycle.ViewModel
import com.example.museoapp.model.FireBase.Auth

class UserProfileViewModel : ViewModel() {
    // TODO: Implement the ViewModel
    private var authObj = Auth()

    fun log_out(){
        authObj.logOut()
    }

    fun update_profile() {

    }
}