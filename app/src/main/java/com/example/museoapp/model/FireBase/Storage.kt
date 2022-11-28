package com.example.museoapp.model.FireBase

import android.content.ContentValues.TAG
import android.graphics.Bitmap
import android.net.Uri
import android.nfc.Tag
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream

class Storage {
    val storage = Firebase.storage
    var storageRef = storage.reference
    var imageRef : StorageReference = storageRef.child("userImage")
    var songRef: StorageReference = storageRef.child("audioGallery")
    var downloadUrl: Uri? = null
    private var galleryFireBase = GalleryFireBase()
    //private var auth = Auth()

    fun uploadFile(auth:Auth,
                   userId: String,
                   name: String,
                   surname: String,
                   tlf: Long,
                   image: Bitmap?,
                   role: Int,
                   userFirebase: MutableLiveData<FirebaseUser?>,
                   error: MutableLiveData<String?>
    ) {
        val baos = ByteArrayOutputStream()
        image?.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()
        val uploadTask = imageRef.child(userId).putBytes(data)

        val urlTask = uploadTask.continueWithTask{ task ->
            if (!task.isSuccessful){
                task.exception?.let {
                    throw it
                }
            }
            imageRef.child(userId).downloadUrl
        }.addOnCompleteListener { task ->
            downloadUrl = task.result
            auth.writeNewUser(userId, name, surname, tlf, downloadUrl.toString(), role, userFirebase, error)
        }.addOnFailureListener{
            auth.writeNewUser(userId, name, surname, tlf, null, role, userFirebase, error)
        }
    }

    fun downloadFile(uid: String){

    }

    //Sube archivos desde local
    fun uploadAudio(id_gallery: String, path: String) {
        var file = Uri.fromFile(File(path))
        val audioRef = storageRef.child(id_gallery+"/${file.lastPathSegment}")

        var uploadTask = audioRef.putFile(file).continueWithTask { task ->
            if (!task.isSuccessful){
                task.exception?.let {
                    throw it
                }
            }
            audioRef.child(id_gallery).downloadUrl
        }.addOnCompleteListener { task ->
            downloadUrl = task.result
            //Se debe actualizar el Gallery en FB
            galleryFireBase.updateAudio(id_gallery, downloadUrl.toString())
        }.addOnFailureListener{
            //Se debe notificar al usuario del error
        }
    }
}