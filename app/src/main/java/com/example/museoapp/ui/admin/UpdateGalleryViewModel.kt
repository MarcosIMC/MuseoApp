package com.example.museoapp.ui.admin

import android.graphics.Bitmap
import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bumptech.glide.Glide
import com.example.museoapp.R
import com.example.museoapp.databinding.ActivityUpdateGalleryBinding
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

    fun loadLabels(
        binding: ActivityUpdateGalleryBinding,
        updateGalleryActivity: UpdateGalleryActivity,
        item_object: GalleryModelSerializable
    ) {
        updateGalleryActivity.supportActionBar?.title = item_object.name
        Glide.with(updateGalleryActivity).load(item_object.image).centerCrop().placeholder(R.drawable.ic_baseline_broken_image_24).error(
            com.google.android.material.R.drawable.mtrl_ic_error).timeout(500).override(204,190).into(binding.imageViewGallery)
        binding.editTextTitleGallery.editText?.setText(item_object.name)
        binding.editTextSortDescriptionGallery.editText?.setText(item_object.sort_description)
        binding.editTextLongDescriptionGallery.editText?.setText(item_object.long_description)
    }

    fun checkLabels(
        binding: ActivityUpdateGalleryBinding,
        updateGalleryActivity: UpdateGalleryActivity
    ): Boolean {
        var isValid = true

        if (TextUtils.isEmpty(binding.editTextTitleGallery.editText?.text.toString())){
            binding.editTextTitleGallery.error = updateGalleryActivity.getString(R.string.error_title_gallery)
            isValid = false
        }else {
            binding.editTextTitleGallery.error = null
        }

        if (TextUtils.isEmpty(binding.editTextSortDescriptionGallery.editText?.text.toString())){
            binding.editTextSortDescriptionGallery.error = updateGalleryActivity.getString(R.string.error_short_description)
            isValid = false
        }else {
            binding.editTextSortDescriptionGallery.error = null
        }

        if (TextUtils.isEmpty(binding.editTextLongDescriptionGallery.editText?.text.toString())){
            binding.editTextLongDescriptionGallery.error = updateGalleryActivity.getString(R.string.error_long_description)
            isValid = false
        }else {
            binding.editTextLongDescriptionGallery.error = null
        }

        return isValid
    }
}