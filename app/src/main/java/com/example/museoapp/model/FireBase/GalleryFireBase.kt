package com.example.museoapp.model.FireBase

import android.content.ContentValues.TAG
import android.provider.ContactsContract.Data
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.museoapp.model.GalleryModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import java.net.URI

class GalleryFireBase {
    private var firebaseDB = FireBase()
    private var myRef = firebaseDB.getRefDB()

    suspend fun getAll(
        galleriesObjects: MutableLiveData<MutableList<GalleryModel>>,
        errorGalleryObject: MutableLiveData<String?>
    ) {
        val galleriesListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val list = mutableListOf<GalleryModel>()
                var gallery : GalleryModel
                for (ds in snapshot.children){
                    Log.i(TAG, "KEY: " + ds.key)
                    gallery =  ds.getValue<GalleryModel>()!!
                    gallery.key = ds.key
                    list.add(gallery)
                }

                if (list.isNotEmpty()){
                    galleriesObjects.value = list
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w(TAG,"loadGallery:onCancelled", error.toException())
                errorGalleryObject.value = "Failed load items. Let´s try later."
            }
        }
        myRef?.child("gallery")?.addValueEventListener(galleriesListener)
    }

    suspend fun getMainImages(galleryImageList: MutableLiveData<ArrayList<String>>,
                              errorGalleryObject: MutableLiveData<String?>) {
        val galleriesListener = object  : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val list = ArrayList<String>()
                var imageURL : String
                for (datasanpshot in snapshot.children){
                    imageURL = datasanpshot.child("image").value.toString()
                    list.add(imageURL)

                    if (datasanpshot.key?.equals(5)!!){
                        break
                    }
                }
                if (list.isNotEmpty()){
                    galleryImageList.value = list
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Log.w(TAG,"loadGallery:onCancelled", error.toException())
                errorGalleryObject.value = "Failed load items. Let´s try later."

            }
        }
        myRef?.child("gallery")?.addValueEventListener(galleriesListener)
    }

    fun updateAudio(id_gallery: String, path: String) {
        myRef?.child("gallery")?.child(id_gallery)?.child("audio")?.setValue(path)?.addOnCompleteListener {
            Log.i(TAG, "Se acaba de actualizar la URL del audio para el objeto: " + id_gallery)
        }?.addOnFailureListener {
            Log.e(TAG, "Se produce un error al intentar actualizar la URL del audio: " + it.toString())
        }
    }
}