package com.example.museoapp.model

import android.net.Uri

data class UserModel(
    val name:String? = null,
    val surname:String? = null,
    val image: String? = null,
    val tlf:Long? = null,
    val gallery:Map<String, Boolean>? = null,
    val role: Int? = 0)
