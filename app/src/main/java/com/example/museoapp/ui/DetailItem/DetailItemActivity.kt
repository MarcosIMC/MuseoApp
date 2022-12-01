package com.example.museoapp.ui.DetailItem

import android.content.ContentValues.TAG
import android.media.AudioManager
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.example.museoapp.R
import com.example.museoapp.ViewModel.DetailItemViewModel
import com.example.museoapp.databinding.ActivityDetailItemBinding
import com.example.museoapp.model.FireBase.Auth
import com.example.museoapp.model.FireBase.UserFireBase
import com.example.museoapp.model.GalleryModelSerializable

class DetailItemActivity : AppCompatActivity() {
    private lateinit var binding:ActivityDetailItemBinding
    private var mediaPlayer: MediaPlayer? = null
    private var url_audio: String? = null
    private var is_playing = false
    private var is_favourite = false
    private var authObj = Auth()
    private var auth = authObj.getAuth()

    var id_item: GalleryModelSerializable? = null
    private val detailViewModel : DetailItemViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_item)

        //Habilitamos la flecha para volver atrás (Parent Activity)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        if (auth?.currentUser != null) {
            detailViewModel.getFavourites()
        }



        binding = ActivityDetailItemBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val bundle = intent.extras
        id_item = bundle?.getSerializable("id_item") as? GalleryModelSerializable

            if (id_item != null){
            val gallery : GalleryModelSerializable? = id_item
            supportActionBar?.title = gallery?.name
            binding.textViewDescription.text = gallery!!.long_description
            url_audio = gallery.audio.toString()
                Log.i(TAG, "Valor del audio: " + url_audio)
            Glide.with(this).load(gallery.image).centerCrop().error(
                R.drawable.ic_baseline_broken_image_24
            ).timeout(600).into(binding.imageGallery)
        }

        detailViewModel.favouritesId.observe(this) {
            if (it.contains(id_item?.key))   {
                is_favourite = true
                updateOptionsMenu(is_playing, is_favourite)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        //return super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.menu_app_bar_media_player, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        //return super.onPrepareOptionsMenu(menu)
        super.onPrepareOptionsMenu(menu)
        val item_star = menu?.findItem(R.id.favourite_button)
        val item_play = menu?.findItem(R.id.play_button)
        val item_pause = menu?.findItem(R.id.pause_button)
        val item_stop = menu?.findItem(R.id.close_button)

        item_star?.isVisible = false
        item_play?.isVisible = false
        item_pause?.isVisible = false
        item_stop?.isVisible = false

        detailViewModel.mensaje.observe(this){
            Toast.makeText(applicationContext, it,
                Toast.LENGTH_SHORT).show()
        }

        if (is_favourite && auth?.currentUser != null) {
            item_star?.isVisible = true
            item_star?.setIcon(R.drawable.ic_baseline_star_24)
        }else if (!is_favourite && auth?.currentUser != null) {
            item_star?.isVisible = true
            item_star?.setIcon(R.drawable.ic_baseline_star_border_24)
        }

        if (!url_audio.isNullOrEmpty()) {
            if (is_playing) {
                item_play!!.isVisible = false
                item_pause!!.isVisible = true
                item_stop!!.isVisible = true
            }else {
                item_play!!.isVisible = true
                item_pause!!.isVisible = false
                item_stop!!.isVisible = false
            }
        }

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (mediaPlayer == null && !url_audio.isNullOrEmpty()) {
            setMediaPlayer()
        }
        return when (item.itemId) {
            R.id.play_button -> {
                if (mediaPlayer == null) { setMediaPlayer() }
                updateOptionsMenu(true, is_favourite)
                mediaPlayer!!.start()

                true
            }

            R.id.pause_button -> {
                updateOptionsMenu(false, is_favourite)
                mediaPlayer!!.pause()

                true
            }

            R.id.close_button -> {
                updateOptionsMenu(false, is_favourite)
                mediaPlayer!!.stop()
                mediaPlayer?.release()
                mediaPlayer = null
                true
            }

            R.id.favourite_button -> {
                updateOptionsMenu(false, !is_favourite)

                if (is_favourite) {
                    detailViewModel.addFavourite(id_item)
                }else {
                    detailViewModel.removeFavourite(id_item)
                }

                true
            }

            else -> { super.onOptionsItemSelected(item) }
        }
    }

    private fun updateOptionsMenu(playing: Boolean, favourite: Boolean) {
        is_playing = playing
        is_favourite = favourite

        this.invalidateOptionsMenu()
    }

    private fun setMediaPlayer() {
        mediaPlayer = MediaPlayer().apply {
            setAudioStreamType(AudioManager.STREAM_MUSIC)
            setDataSource(url_audio)
            //En caso de que tarde mucho, prepareAsync()
            prepare()

            setOnCompletionListener {
                updateOptionsMenu(false, is_favourite)
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release()
    }
}