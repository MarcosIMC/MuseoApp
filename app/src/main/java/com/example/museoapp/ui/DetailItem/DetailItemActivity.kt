package com.example.museoapp.ui.DetailItem

import android.content.ContentValues.TAG
import android.media.AudioManager
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.bumptech.glide.Glide
import com.example.museoapp.R
import com.example.museoapp.databinding.ActivityDetailItemBinding
import com.example.museoapp.model.GalleryModelSerializable

class DetailItemActivity : AppCompatActivity() {
    private lateinit var binding:ActivityDetailItemBinding
    private var mediaPlayer: MediaPlayer? = null
    private var url_audio: String? = null
    private var is_playing = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_item)

        //Habilitamos la flecha para volver atrás (Parent Activity)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        binding = ActivityDetailItemBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val bundle = intent.extras
        val id_item = bundle?.getSerializable("id_item") as? GalleryModelSerializable

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
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        //return super.onCreateOptionsMenu(menu)
        if (!url_audio.isNullOrEmpty()) {
            menuInflater.inflate(R.menu.menu_app_bar_media_player, menu)
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        //return super.onPrepareOptionsMenu(menu)
        super.onPrepareOptionsMenu(menu)
        val item_play = menu?.findItem(R.id.play_button)
        val item_pause = menu?.findItem(R.id.pause_button)
        val item_stop = menu?.findItem(R.id.close_button)

        if (is_playing) {
            item_play!!.isVisible = false
            item_pause!!.isVisible = true
            item_stop!!.isVisible = true
        }else if (!is_playing && !url_audio.isNullOrEmpty()) {
            item_play!!.isVisible = true
            item_pause!!.isVisible = false
            item_stop!!.isVisible = false
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
                updateOptionsMenu(true)
                mediaPlayer!!.start()

                true
            }

            R.id.pause_button -> {
                updateOptionsMenu(false)
                mediaPlayer!!.pause()

                true
            }

            R.id.close_button -> {
                updateOptionsMenu(false)
                mediaPlayer!!.stop()
                mediaPlayer?.release()
                mediaPlayer = null
                true
            }

            else -> { super.onOptionsItemSelected(item) }
        }
    }

    private fun updateOptionsMenu(playing: Boolean) {
        is_playing = playing
        this.invalidateOptionsMenu()
    }

    private fun setMediaPlayer() {
        mediaPlayer = MediaPlayer().apply {
            setAudioStreamType(AudioManager.STREAM_MUSIC)
            setDataSource(url_audio)
            //En caso de que tarde mucho, prepareAsync()
            prepare()

            setOnCompletionListener {
                updateOptionsMenu(false)
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release()
    }
}