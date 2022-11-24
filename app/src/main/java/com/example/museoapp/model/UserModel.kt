package com.example.museoapp.model

import android.net.Uri

data class UserModel(
    val name:String? = null,
    val surname:String? = null,
    //val email:String,
    val image: String? = null,
    val tlf:Long? = null,
    //val favourites:List<GalleryModel>,
    val role: Int? = 0)
