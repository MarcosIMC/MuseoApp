package com.example.museoapp.ui.admin

import android.graphics.Bitmap
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.museoapp.model.FireBase.GalleryFireBase
import com.example.museoapp.model.GalleryModelSerializable

class UpdateGalleryViewModel: ViewModel() {
    private var galleryFireBase = GalleryFireBase()
    val message = MutableLiveData<String?>()
    val error = MutableLiveData<String?>()

    fun updateGallery(
        item_object: GalleryModelSerializable?,
        image: Bitmap?,
        title: String,
        sort_description: String,
        long_description: String
    ) {
        galleryFireBase.updateGallery(item_object?.key, image, item_object?.audio, long_description, sort_description, title,
            item_object?.qr_code!!, message, error
        )
    }
}