package com.example.museoapp.model.FireBase

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.museoapp.model.UserModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue

class UserFireBase {
    private var firebaseBD = FireBase()
    private var myRef = firebaseBD.getRefDB()

    fun getUserData(id_user: String, userObject: MutableLiveData<UserModel>, errorUSerObject: MutableLiveData<String?>){
        myRef?.child("users")?.child(id_user)?.get()?.addOnSuccessListener {
            Log.i(TAG, "Valor del FireBase: " + it.value)
            userObject.value = it.getValue<UserModel>()
        }?.addOnFailureListener {
            errorUSerObject.value = it.toString()
        }
    }
}