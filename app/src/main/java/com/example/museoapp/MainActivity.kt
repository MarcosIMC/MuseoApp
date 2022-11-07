package com.example.museoapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.example.museoapp.ViewModel.GalleryViewModel
import com.example.museoapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val galleryViweModel : GalleryViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val bundle : Bundle

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        galleryViweModel.galleriesObjects.observe(this) { listGalleries ->
            if (listGalleries != null) {
                binding.progress.isVisible = false
                val intent = Intent(this, MainViewActivity::class.java)
                intent.putExtra("ListObjects", listGalleries.toString())
                startActivity(intent)
            }
        }

        galleryViweModel.isLoading.observe(this, Observer {
            binding.progress.isVisible = it
        })

        galleryViweModel.error.observe(this, { errorMessage ->
            Toast.makeText(applicationContext, errorMessage,
                Toast.LENGTH_SHORT).show()
        })
    }

    override fun onStart() {
        super.onStart()
        galleryViweModel.getAllElements()
    }


}