package com.example.museoapp.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.museoapp.model.FireBase.Auth
import com.example.museoapp.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginViewModel : ViewModel() {
    private var authObj = Auth()
    val userFirebase = MutableLiveData<FirebaseUser?>()
    val error = MutableLiveData<String?>()

    fun signInWithEmail(email:String, password:String){
        val activity = LoginActivity()
        authObj.signInWithEmailAndPassword(email, password, activity, userFirebase, error)
    }

    fun checkLogged(): Boolean{
        if(authObj.checkUserSigned()){
            return true
        }
        return false
    }
}