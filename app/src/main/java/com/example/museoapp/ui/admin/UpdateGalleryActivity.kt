package com.example.museoapp.ui.admin

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.museoapp.R
import com.example.museoapp.databinding.ActivityUpdateGalleryBinding
import com.example.museoapp.model.GalleryModelSerializable

class UpdateGalleryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUpdateGalleryBinding
    private var drawable: BitmapDrawable? = null
    private val updateGalleryViewModel: UpdateGalleryViewModel by viewModels()
    var item_object: GalleryModelSerializable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_gallery)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        binding = ActivityUpdateGalleryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bundle = intent.extras
        item_object = bundle?.getSerializable("id_item") as GalleryModelSerializable?

        if (item_object?.key != null){
            loadLabels()
        }

        //Permisos galeria
        binding.buttonGallery.setOnClickListener { requestPermission() }
    }

    private fun loadLabels() {
        supportActionBar?.title = item_object?.name
        Glide.with(this).load(item_object?.image).centerCrop().placeholder(R.drawable.ic_baseline_broken_image_24).error(
            com.google.android.material.R.drawable.mtrl_ic_error).timeout(500).override(204,190).into(binding.imageViewGallery)
        binding.editTextTitleGallery.editText?.setText(item_object?.name)
        binding.editTextSortDescriptionGallery.editText?.setText(item_object?.sort_description)
        binding.editTextLongDescriptionGallery.editText?.setText(item_object?.long_description)
    }

    //Abrir la galeria de imagenes
    //Comprobamos los permisos del usuairo
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ){isGranted ->
        if (isGranted) {
            pickPhotoFormGallery()
        }else {
            Toast.makeText(this, R.string.error_permission_gallery, Toast.LENGTH_SHORT).show()
        }
    }

    private fun requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            when {
                ContextCompat.checkSelfPermission(
                    this, Manifest.permission.READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED -> {
                    pickPhotoFormGallery()
                }

                else -> requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
            }
        }else {
            pickPhotoFormGallery()
        }
    }

    //Iniciamos la actividad de la galeria
    private val startForActivityGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ){result ->
        if (result.resultCode == Activity.RESULT_OK){
            val data = result.data?.data
            binding.imageViewGallery.setImageURI(data)
            drawable = binding.imageViewGallery.drawable as BitmapDrawable?
        }
    }

    //Abrimos la galeria
    private fun pickPhotoFormGallery() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        startForActivityGallery.launch(intent)
    }

    //Agregamos las opciones del menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.menu_app_bar_user_update, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.check_update_button -> {
                if (checkLabels()) {
                    updateGalleryViewModel.updateGallery(
                        item_object,
                        drawable?.bitmap,
                        binding.editTextTitleGallery.editText?.text.toString(),
                        binding.editTextSortDescriptionGallery.editText?.text.toString(),
                        binding.editTextLongDescriptionGallery.editText?.text.toString()
                    )
                }
                true
            }else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    private fun checkLabels(): Boolean {
        var isValid = true

        if (TextUtils.isEmpty(binding.editTextTitleGallery.editText?.text.toString())){
            binding.editTextTitleGallery.error = getString(R.string.error_title_gallery)
            isValid = false
        }else {
            binding.editTextTitleGallery.error = null
        }

        if (TextUtils.isEmpty(binding.editTextSortDescriptionGallery.editText?.text.toString())){
            binding.editTextSortDescriptionGallery.error = getString(R.string.error_sort_description)
            isValid = false
        }else {
            binding.editTextSortDescriptionGallery.error = null
        }

        if (TextUtils.isEmpty(binding.editTextLongDescriptionGallery.editText?.text.toString())){
            binding.editTextLongDescriptionGallery.error = getString(R.string.error_long_description)
            isValid = false
        }else {
            binding.editTextLongDescriptionGallery.error = null
        }

        return isValid
    }
}