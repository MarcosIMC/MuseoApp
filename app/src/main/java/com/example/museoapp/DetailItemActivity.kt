package com.example.museoapp

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.museoapp.databinding.ActivityDetailItemBinding
import com.example.museoapp.model.GalleryModel
import com.example.museoapp.model.GalleryModelSerializable

class DetailItemActivity : AppCompatActivity() {
    private lateinit var binding:ActivityDetailItemBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_item)

        binding = ActivityDetailItemBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val bundle = intent.extras
        val id_item = bundle?.getSerializable("id_item") as? GalleryModelSerializable

        if (id_item != null){
            val gallery : GalleryModelSerializable? = id_item
            binding.textViewTitle.text = gallery!!.name
            binding.textViewDescription.text = gallery!!.long_description

        }
    }
}