package com.example.museoapp.ui.user

import android.content.Intent
import android.text.TextUtils
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.museoapp.LoginActivity
import com.example.museoapp.R
import com.example.museoapp.databinding.FragmentUserLoginBinding
import com.example.museoapp.model.FireBase.Auth
import com.google.firebase.auth.FirebaseUser

class UserViewModel : ViewModel() {

    private var authObj = Auth()
    val userFirebase = MutableLiveData<FirebaseUser?>()
    val isAdmin = MutableLiveData<Boolean?>()
    val error = MutableLiveData<String?>()

    fun signInWithEmail(email:String, password:String){
        val activity = LoginActivity()
        authObj.signInWithEmailAndPassword(email, password, activity, userFirebase, error)
    }

    fun isAdmin() {
        authObj.isAdmin(isAdmin)
    }

    fun checkLogged(): Boolean{
        if(authObj.checkUserSigned()){
            return true
        }
        return false
    }
    fun checkLabels(binding: FragmentUserLoginBinding, userFragment: UserFragment): Boolean {
        var isValid = true
        if (TextUtils.isEmpty(binding.textEmail.editText?.text.toString())){
            binding.textEmail.error = userFragment.getString(R.string.error_email_user)
            isValid = false
        }else {
            binding.textEmail.error = null
        }

        if (TextUtils.isEmpty(binding.textPassword.editText?.text.toString())){
            binding.textPassword.error = userFragment.getString(R.string.error_password_user)
            isValid = false
        }else {
            binding.textPassword.error = null
        }
        return  isValid
    }

    fun launchSignUp(userFragment: FragmentActivity?) {
        val intent = Intent(userFragment, RegisterUserActivity::class.java)
        userFragment!!.startActivity(intent)
    }
}