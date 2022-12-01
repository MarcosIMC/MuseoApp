package com.example.museoapp.model.FireBase

import android.content.ContentValues.TAG
import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.museoapp.model.GalleryModel
import com.example.museoapp.model.UserModel
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue

class UserFireBase {
    private var firebaseBD = FireBase()
    private var myRef = firebaseBD.getRefDB()
    private var listGallery: MutableMap<String, Boolean>? = null

    fun getUserData(id_user: String, userObject: MutableLiveData<UserModel>, errorUSerObject: MutableLiveData<String?>){
        myRef?.child("users")?.child(id_user)?.get()?.addOnSuccessListener {
            Log.i(TAG, "Valor del FireBase: " + it.value)
            userObject.value = it.getValue<UserModel>()
            listGallery = (userObject.value?.gallery as MutableMap<String, Boolean>?)!!
            Log.i(TAG, "Valor de listGallery: " + listGallery!!.keys)
        }?.addOnFailureListener {
            errorUSerObject.value = it.toString()
        }
    }

    fun getListGallery(): Map<String, Boolean> {
        Log.i(TAG, "Dentro del getGallery")
        if (listGallery?.isNotEmpty()!!) {
            Log.i(TAG, "Retorna la lista")
            return listGallery as MutableMap<String, Boolean>
        }

        return emptyMap<String, Boolean>()
    }

    fun updateImageUser(downloadUrl: Uri) {

    }
}