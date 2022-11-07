package com.example.museoapp.model.FireBase

import android.content.ContentValues.TAG
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
        galleriesObjects: MutableLiveData<List<GalleryModel>>,
        errorGalleryObject: MutableLiveData<String?>
    ) {
        //myRef?.child("gallery")?.get()?.addOnSuccessListener {
            /*Log.i("Firebase Value", "Value: ${it.value}")
            var items = listOf<GalleryModel>(it.value as GalleryModel)
            for (i in items){
                galleriesObjects.value = i
            }
            //galleriesObjects.value = it.value as GalleryModel?
        }?.addOnFailureListener {
            Log.e("Firebase Value", "Error getting data", it)
        }*/
        val galleriesListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val list = mutableListOf<GalleryModel>()
                Log.i("VALOR DEL DATASNAP: ", "${snapshot.value}")
                list.add(snapshot.getValue(GalleryModel::class.java)!!)
                galleriesObjects.value = list
                /*val galleryElement = snapshot.getValue<GalleryModel>()
                print("VALOR GALLERY ELEMENT: " + galleryElement?.name)
                galleriesObjects.value = galleryElement*/
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w(TAG,"loadGallery:onCancelled", error.toException())
                errorGalleryObject.value = "Failed load items. Let´s try later."
            }
        }
        myRef?.child("gallery")?.child("1")?.addValueEventListener(galleriesListener)
    }
}