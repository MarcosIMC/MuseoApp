package com.example.museoapp.model.FireBase

import android.content.ContentValues.TAG
import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.museoapp.R
import com.example.museoapp.model.UserModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue

class UserFireBase {
    private var firebaseBD = FireBase()
    private var myRef = firebaseBD.getRefDB()
    private var listGallery: MutableMap<String, Boolean>? = null
    private var authObj = Auth()
    private var auth = authObj.getAuth()


    fun getUserData(id_user: String, userObject: MutableLiveData<UserModel>, errorUSerObject: MutableLiveData<String?>){
        myRef?.child("users")?.child(id_user)?.get()?.addOnSuccessListener {
            Log.i(TAG, "Valor del FireBase: " + it.value)
            userObject.value = it.getValue<UserModel>()
            if (userObject.value?.gallery != null){
                listGallery = (userObject.value?.gallery as MutableMap<String, Boolean>?)!!
            }
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

    fun removeFavourite(id_gallery: String?, mensaje: MutableLiveData<String?>) {
        id_gallery?.let {
            myRef?.child("users")?.child(auth!!.uid!!)?.child("gallery")?.child(it)?.setValue(null)?.addOnSuccessListener {
                mensaje.value = "Se eliminó de favoritos."
            }?.addOnFailureListener {
                Log.i(TAG, "No se pudo añadir a favoritos")
                mensaje.value =  "No se pudo desmarcar de favorito."
            }
        }
    }

    fun addFavourites(id_gallery: String?, mensaje: MutableLiveData<String?>, ) {
        id_gallery?.let {
            myRef?.child("users")?.child(auth!!.uid!!)?.child("gallery")?.child(it)?.setValue(true)?.addOnSuccessListener {
                mensaje.value = "Se añadió a favoritos."
            }?.addOnFailureListener {
                Log.i(TAG, "No se pudo añadir a favoritos")
                mensaje.value = "No se pudo añadir a favoritos."
            }
        }
    }

    fun getFavourites(
        favouriteList: MutableLiveData<MutableList<String>>,
                              errorGalleryObject: MutableLiveData<String?>) {
        val favourites = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val list = mutableListOf<String>()
                for (ds in snapshot.children) {
                    list.add(ds.key!!)
                }

                if (list.isNotEmpty()){
                    favouriteList.value = list
                }
            }

            override fun onCancelled(error: DatabaseError) {
                errorGalleryObject.value = "Failed load items. Let´s try later."
            }

        }
         myRef?.child("users")?.child(auth!!.uid!!)?.child("gallery")?.addValueEventListener(favourites)
    }

    fun updateImageUser(downloadUrl: Uri) {

    }
}