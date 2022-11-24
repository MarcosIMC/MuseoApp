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

class Storage {
    val storage = Firebase.storage
    var storageRef = storage.reference
    var imageRef : StorageReference = storageRef.child("userImage")
    var downloadUrl: Uri? = null
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
}