package com.example.museoapp.ViewModel

import android.graphics.Bitmap
import android.net.Uri
import android.widget.EditText
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.museoapp.model.FireBase.Auth
import com.example.museoapp.ui.UpdateForm.UpdateProfileFormActivity
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.userProfileChangeRequest

class UpdateProfileViewModel : ViewModel(){
    private var authObj = Auth()
    val userFirebase = MutableLiveData<FirebaseUser?>()
    val error = MutableLiveData<String?>()

    fun updateUser(
        //image: Bitmap?,
        email: EditText,
        name: EditText,
        surname: EditText,
        phone: EditText,
        password: EditText
    ) {
        authObj.updateUserDataWithPass(email.text.toString(), password.text.toString(), name.text.toString(),
        surname.text.toString(), phone.text.toString().toLong(), "", UpdateProfileFormActivity(), userFirebase, error)
    }
}