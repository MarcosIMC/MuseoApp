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
}