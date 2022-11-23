package com.example.museoapp.ui.home

import android.content.Intent
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import com.example.museoapp.ui.DetailItem.DetailItemActivity
import com.example.museoapp.model.GalleryModel
import com.example.museoapp.model.GalleryModelSerializable

class HomeViewModel : ViewModel() {

    fun itemClicked(position: Int, it: MutableList<GalleryModel>, activity: FragmentActivity?, ){
        val item = GalleryModelSerializable(it[position])
        val intent = Intent(activity, DetailItemActivity::class.java)
        intent.putExtra("id_item", item)
        activity!!.startActivity(intent)
    }
}