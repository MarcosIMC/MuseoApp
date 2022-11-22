package com.example.museoapp.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.museoapp.ui.user.RegisterUserActivity
import com.example.museoapp.model.FireBase.Auth
import com.google.firebase.auth.FirebaseUser

class SignUpViewModel : ViewModel() {
    private var authObj = Auth()

    val userFirebase = MutableLiveData<FirebaseUser?>()
    val error = MutableLiveData<String?>()

    fun createUser(email : String, password : String, name : String, surname : String, tlf : Long)  {
        val activity = RegisterUserActivity()
        authObj.createUserWithEmailAndPassword(email, password, name, surname, tlf, "", activity, userFirebase, error)
    }
}