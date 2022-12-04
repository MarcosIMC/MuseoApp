package com.example.museoapp.model.FireBase

import android.content.ContentValues.TAG
import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.museoapp.model.GalleryModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import kotlin.streams.asSequence

class GalleryFireBase {
    private var firebaseDB = FireBase()
    private var myRef = firebaseDB.getRefDB()
    private var storage = Storage()

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

    fun createGallery(image: Bitmap?, name: String, sort_description: String, long_description: String, message: MutableLiveData<String?>, error: MutableLiveData<String?>) {
        val key = generateKey()

        if (image != null) {
            storage.uploadImageGallery(GalleryFireBase(), key, image, name, sort_description, long_description, message, error)
        }
        newGallery(key, null, null, long_description, name, sort_description, key, message, error)
    }

    fun newGallery(
        key: String,
        urlImage: String?,
        audio: String?,
        long_description: String,
        name: String,
        sort_description: String,
        qr_code: String,
        message: MutableLiveData<String?>,
        error: MutableLiveData<String?>
    ) {
        val gallery = GalleryModel(null, "", urlImage, long_description, sort_description, name, qr_code)

        myRef?.child("gallery")?.child(key)?.setValue(gallery)?.addOnCompleteListener {
            message.value = "Se ha creado el objeto."
        }?.addOnFailureListener {
            error.value = "No se pudo crear el objeto."
        }
    }

    private fun generateKey(): String{
        val source = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
        return java.util.Random().ints(20, 0, source.length).asSequence().map(source::get).joinToString("")
    }

    fun updateGallery(key: String?, image: Bitmap?, audio: String?, long_description: String, sort_description: String, name: String, qr_code: String, message: MutableLiveData<String?>, error: MutableLiveData<String?>) {
        if (image != null) {
            storage.updateImageGallery(GalleryFireBase(), key!!, image, audio, name, sort_description, long_description, message, error)
        }
        updateObjectGallery(key, null, audio, long_description, sort_description, name, qr_code, message, error)
    }

    fun updateObjectGallery(
        key: String?,
        image: String?,
        audio: String?,
        long_description: String,
        sort_description: String,
        name: String,
        qr_code: String,
        message: MutableLiveData<String?>,
        error: MutableLiveData<String?>
    ) {
        val gallery = GalleryModel(null, audio, image, long_description, sort_description, name, qr_code)

        myRef?.child("gallery")?.child(key!!)?.setValue(gallery)?.addOnCompleteListener {
            message.value = "Se ha actualizado el objeto."
        }?.addOnFailureListener {
            error.value = "No se pudo actualizar el objeto."
        }
    }
}