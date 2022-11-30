package com.example.museoapp.ViewModel

import android.content.Context
import android.content.Intent
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.museoapp.R
import com.example.museoapp.model.FireBase.Auth
import com.example.museoapp.model.FireBase.GalleryFireBase
import com.example.museoapp.model.FireBase.UserFireBase
import com.example.museoapp.model.GalleryModel
import com.example.museoapp.model.GalleryModelSerializable
import com.example.museoapp.ui.DetailItem.DetailItemActivity

class ListFavouritesViewModel : ViewModel() {
    private var authObj = Auth()
    private var auth = authObj.getAuth()
    val galleryList = MutableLiveData<Map<String, Boolean>>()
    val galleryFireBase = GalleryFireBase()
    val userFireBase = UserFireBase()
    val error = MutableLiveData<String?>()
    val galleriesObjects = MutableLiveData<MutableList<GalleryModel>>()

    fun itemClicked(position: Int, it: MutableList<GalleryModel>, applicationContext: Context?) {
        val item = GalleryModelSerializable(it[position])
        val intent = Intent(applicationContext, DetailItemActivity::class.java)
        intent.putExtra("id_item", item)
        applicationContext!!.startActivity(intent)
    }
}