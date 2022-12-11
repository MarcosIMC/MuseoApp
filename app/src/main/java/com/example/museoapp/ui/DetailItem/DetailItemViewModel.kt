package com.example.museoapp.ui.DetailItem

import android.content.ContentValues
import android.media.MediaPlayer
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bumptech.glide.Glide
import com.example.museoapp.R
import com.example.museoapp.databinding.ActivityDetailItemBinding
import com.example.museoapp.model.FireBase.Auth
import com.example.museoapp.model.FireBase.UserFireBase
import com.example.museoapp.model.GalleryModelSerializable

class DetailItemViewModel : ViewModel() {
    val favouritesId = MutableLiveData<MutableList<String>>()
    val userFireBase = UserFireBase()
    val error = MutableLiveData<String?>()
    val mensaje = MutableLiveData<String?>()
    private var authObj = Auth()
    private var auth = authObj.getAuth()


    fun getFavourites() {
        userFireBase.getFavourites(favouritesId, error, auth)
    }

    fun addFavourite(id_item: GalleryModelSerializable?) {
        userFireBase.addFavourites(id_item?.key, mensaje, auth)
    }

    fun removeFavourite(id_item: GalleryModelSerializable?) {
        userFireBase.removeFavourite(id_item?.key, mensaje, auth)
    }

    fun setDatas(
        detailItemActivity: DetailItemActivity,
        binding: ActivityDetailItemBinding,
        id_item: GalleryModelSerializable
    ): String? {
        val gallery : GalleryModelSerializable = id_item
        detailItemActivity.supportActionBar?.title = gallery?.name
        binding.textViewDescription.text = gallery!!.long_description
        val url_audio = gallery.audio.toString()
        Log.i(ContentValues.TAG, "Valor del audio: " + url_audio)
        Glide.with(detailItemActivity).load(gallery.image).centerInside().error(
            R.drawable.ic_baseline_broken_image_24
        ).timeout(600).into(binding.imageGallery)
        return url_audio
    }

    fun updateOptionsMenu(
        playing: Boolean,
        favourite: Boolean,
        detailItemActivity: DetailItemActivity
    ) {
        detailItemActivity.is_playing = playing
        detailItemActivity.is_favourite = favourite

        detailItemActivity.invalidateOptionsMenu()
    }

    fun startAudio(detailItemActivity: DetailItemActivity) {
        detailItemActivity.mediaPlayer!!.start()
    }

    fun pauseAudio(detailItemActivity: DetailItemActivity) {
        detailItemActivity.mediaPlayer!!.pause()
    }

    fun stopAudio(detailItemActivity: DetailItemActivity) {
        detailItemActivity.mediaPlayer!!.stop()
        detailItemActivity.mediaPlayer?.release()
    }
}