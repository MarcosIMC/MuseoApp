package com.example.museoapp.ui.DetailItem

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.museoapp.R
import com.example.museoapp.databinding.ActivityDetailItemBinding
import com.example.museoapp.model.GalleryModelSerializable

class DetailItemActivity : AppCompatActivity() {
    private lateinit var binding:ActivityDetailItemBinding

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
            Glide.with(this).load(gallery.image).centerCrop().error(
                R.drawable.ic_baseline_broken_image_24
            ).timeout(600).into(binding.imageGallery)
        }
    }
}