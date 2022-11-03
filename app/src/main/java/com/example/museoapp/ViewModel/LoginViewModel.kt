package com.example.museoapp.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.museoapp.model.FireBase.Auth
import com.example.museoapp.LoginActivity
import com.example.museoapp.model.UserModel
import com.google.firebase.auth.FirebaseUser

class LoginViewModel : ViewModel() {
    private var authObj = Auth()
    private var auth = authObj.getAuth()

    val userFirebase = MutableLiveData<FirebaseUser?>()
    val error = MutableLiveData<String?>()

    fun signInWithEmail(email:String, password:String){
        val activity = LoginActivity()
        authObj.signInWithEmailAndPassword(email, password, activity, auth)
        userFirebase.postValue(authObj.getUser())
        error.postValue(authObj.getError())
    }

    fun checkLogged(): Boolean{
        if(authObj.checkUserSigned(auth)){
            return true
        }
        return false
    }


}