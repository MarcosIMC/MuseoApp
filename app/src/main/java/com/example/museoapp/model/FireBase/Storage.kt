package com.example.museoapp.model.FireBase

import android.graphics.Bitmap
import android.net.Uri
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import java.io.ByteArrayOutputStream
import java.io.File

class Storage {
    val storage = Firebase.storage
    var storageRef = storage.reference
    var imageRef : StorageReference = storageRef.child("userImage")
    var songRef: StorageReference = storageRef.child("audioGallery")
    var galleryRef : StorageReference = storageRef.child("galleryImage")
    var downloadUrl: Uri? = null
    //private var galleryFireBase = GalleryFireBase()
    //private var auth = Auth()

    fun uploadFile(auth:Auth,
                   userId: String,
                   name: String,
                   surname: String,
                   tlf: Long,
                   image: Bitmap?,
                   gallery: MutableMap<String, Boolean>,
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
            auth.writeNewUser(userId, name, surname, tlf, downloadUrl.toString(), gallery, role, userFirebase, error)
        }.addOnFailureListener{
            auth.writeNewUser(userId, name, surname, tlf, null, gallery, role, userFirebase, error)
        }
    }

    fun uploadImageGallery(
        galleryFireBase: GalleryFireBase,
        key: String,
        image: Bitmap,
        title: String,
        sortDescription: String,
        longDescription: String,
        message: MutableLiveData<String?>,
        error: MutableLiveData<String?>
    ) {
        val baos = ByteArrayOutputStream()
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()
        val uploadTask = galleryRef.child(key).putBytes(data)

        val urlTask = uploadTask.continueWithTask{ task ->
            if (!task.isSuccessful){
                task.exception?.let {
                    throw it
                }
            }
            galleryRef.child(key).downloadUrl
        }.addOnCompleteListener { task ->
            downloadUrl = task.result
            galleryFireBase.newGallery(key, downloadUrl.toString(), null, longDescription, title, sortDescription, key, message, error)
        }.addOnFailureListener{
            galleryFireBase.newGallery(key, null, null, longDescription, title, sortDescription, key, message, error)
        }
    }

    fun downloadFile(uid: String){

    }

    fun updateImageGallery(
        galleryFireBase: GalleryFireBase,
        key: String,
        image: Bitmap,
        audio: String?,
        name: String,
        sortDescription: String,
        longDescription: String,
        message: MutableLiveData<String?>,
        error: MutableLiveData<String?>
    ) {
        val baos = ByteArrayOutputStream()
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()
        val uploadTask = galleryRef.child(key).putBytes(data)

        val urlTask = uploadTask.continueWithTask{ task ->
            if (!task.isSuccessful){
                task.exception?.let {
                    throw it
                }
            }
            galleryRef.child(key).downloadUrl
        }.addOnCompleteListener { task ->
            downloadUrl = task.result
            galleryFireBase.updateObjectGallery(key, downloadUrl.toString(), audio, longDescription, name, sortDescription, key, message, error)
        }.addOnFailureListener{
            galleryFireBase.updateObjectGallery(key, null, null, longDescription, name, sortDescription, key, message, error)
        }
    }

    //Sube archivos desde local
    /*fun uploadAudio(id_gallery: String, path: String) {
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
    }*/
}