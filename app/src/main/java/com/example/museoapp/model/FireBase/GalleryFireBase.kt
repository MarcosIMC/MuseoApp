package com.example.museoapp.model.FireBase

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.museoapp.R
import com.example.museoapp.model.GalleryModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue

class GalleryFireBase {
    private var firebaseDB = FireBase()
    private var myRef = firebaseDB.getRefDB()

    /*
            for (i in 0..listIds.size-1) {
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
            myRef?.child("gallery")?.child("id_user_6")?.addValueEventListener(galleriesListener)
        }

                myRef?.child("gallery")?.child(listIds.elementAt(0))?.get()?.addOnSuccessListener {
            Log.i(TAG, "Valor: ${it.value}")
            galleriesObjects.value = it.value as MutableList<GalleryModel>?
        }?.addOnFailureListener {
            errorGalleryObject.value = R.string.error_gallery.toString()
        }
     */

    fun getSelected(
        listIds: Set<String>,
        galleriesObjects: MutableLiveData<MutableList<GalleryModel>>,
        errorGalleryObject: MutableLiveData<String?>) {
        Log.i(TAG, "Valor del primer elemento del SET: " + listIds.elementAt(0))
        val list = mutableListOf<GalleryModel>()

        for (i in 0..listIds.size-1) {
            val galleriesListener = object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val gallery : GalleryModel? = snapshot.getValue<GalleryModel>()
                    gallery?.key = listIds.elementAt(i)
                    list.add(gallery!!)

                    if (list.isNotEmpty()){
                        Log.i(TAG, "Lista no vacia")
                        galleriesObjects.value = list
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                    Log.w(TAG,"loadGallery:onCancelled", error.toException())
                    errorGalleryObject.value = "Failed load items. Let´s try later."
                }
            }
            myRef?.child("gallery")?.child(listIds.elementAt(i))?.addValueEventListener(galleriesListener)
        }
    }

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