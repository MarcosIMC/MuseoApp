package com.example.museoapp.model

data class UserModel(
    val name:String,
    val surname:String,
    //val email:String,
    val image:String,
    val tlf:Long,
    //val favourites:List<GalleryModel>,
    val role: Int)
