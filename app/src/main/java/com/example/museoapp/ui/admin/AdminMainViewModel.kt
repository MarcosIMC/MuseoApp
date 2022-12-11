package com.example.museoapp.ui.admin

import android.content.Intent
import androidx.lifecycle.ViewModel
import com.example.museoapp.model.FireBase.Auth
import com.example.museoapp.model.GalleryModel
import com.example.museoapp.model.GalleryModelSerializable

class AdminMainViewModel : ViewModel() {
    private var authObj = Auth()

    fun itemClicked(position: Int, it: MutableList<GalleryModel>, activity: AdminMainActivity, ){
        val item = GalleryModelSerializable(it[position])
        val intent = Intent(activity, UpdateGalleryActivity::class.java)
        intent.putExtra("id_item", item)
        activity.startActivity(intent)
    }

    fun log_out(){
        authObj.logOut()
    }

    fun startFormAdd(adminMainActivity: AdminMainActivity) {
        val intent = Intent(adminMainActivity, CreateGalleryActivity::class.java)
        adminMainActivity.startActivity(intent)
    }

}