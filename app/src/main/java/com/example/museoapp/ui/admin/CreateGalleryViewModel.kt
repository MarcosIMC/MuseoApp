package com.example.museoapp.ui.admin

import android.content.Intent
import android.graphics.Bitmap
import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.museoapp.R
import com.example.museoapp.databinding.ActivityCreateGalleryBinding
import com.example.museoapp.model.FireBase.GalleryFireBase
import com.example.museoapp.ui.camera.CameraActivity

class CreateGalleryViewModel: ViewModel() {
    private var galleryFireBase = GalleryFireBase()
    val message = MutableLiveData<String?>()
    val error = MutableLiveData<String?>()

    fun createGallery(
        image: Bitmap?,
        title: String,
        sort_description: String,
        long_description: String
    ){
        galleryFireBase.createGallery(image, title, sort_description, long_description, message, error)
    }

    fun startCamera(createGalleryActivity: CreateGalleryActivity) {
        val intent = Intent(createGalleryActivity, CameraActivity::class.java)
        createGalleryActivity.startActivity(intent)
    }

    fun checkLabels(
        binding: ActivityCreateGalleryBinding,
        createGalleryActivity: CreateGalleryActivity
    ): Boolean {
        var isValid = true

        if (TextUtils.isEmpty(binding.editTextTitleGallery.editText?.text.toString())){
            binding.editTextTitleGallery.error = createGalleryActivity.getString(R.string.error_title_gallery)
            isValid = false
        }else {
            binding.editTextTitleGallery.error = null
        }

        if (TextUtils.isEmpty(binding.editTextSortDescriptionGallery.editText?.text.toString())){
            binding.editTextSortDescriptionGallery.error = createGalleryActivity.getString(R.string.error_short_description)
            isValid = false
        }else {
            binding.editTextSortDescriptionGallery.error = null
        }

        if (TextUtils.isEmpty(binding.editTextLongDescriptionGallery.editText?.text.toString())){
            binding.editTextLongDescriptionGallery.error = createGalleryActivity.getString(R.string.error_long_description)
            isValid = false
        }else {
            binding.editTextLongDescriptionGallery.error = null
        }

        return isValid
    }
}