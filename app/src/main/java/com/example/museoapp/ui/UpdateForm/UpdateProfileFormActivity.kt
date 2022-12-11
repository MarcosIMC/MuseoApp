package com.example.museoapp.ui.UpdateForm

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.example.museoapp.R
import com.example.museoapp.ViewModel.UserDataViewModel
import com.example.museoapp.databinding.ActivityUpdateProfileFormBinding
import com.example.museoapp.model.FireBase.Auth
import com.example.museoapp.model.FireBase.UserFireBase

class UpdateProfileFormActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUpdateProfileFormBinding
    private val updateProfileViewModel: UpdateProfileViewModel by viewModels()
    private val userViewModel : UserDataViewModel by viewModels()
    private var authObj = Auth()
    private var userFirebase = UserFireBase()
    private var auth = authObj.getAuth()
    private var drawable : BitmapDrawable? = null
    private var favourites: MutableMap<String, Boolean>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_profile_form)
        val currentUser = auth?.currentUser

        binding = ActivityUpdateProfileFormBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Habilitamos la flecha para volver atrás (Parent Activity)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        binding.buttonGaleria.setOnClickListener { requestPermission() }

        userViewModel.getUserDatas(currentUser!!.uid)
        userViewModel.userDatas.observe(this) {
            if (it != null){
                if (it.gallery != null){
                    favourites = it.gallery as MutableMap<String, Boolean>
                }
                updateProfileViewModel.loadLabels(currentUser, it, binding)
            }
        }

        updateProfileViewModel.userFirebase.observe(this) {
            if (it != null) {
                updateProfileViewModel.updateUI(this)
            }
        }

        updateProfileViewModel.error.observe(this) {
            if (it != null){
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        }
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
                if (updateProfileViewModel.checkLabels(binding, this) && updateProfileViewModel.checkPassword(binding, this)) {
                    updateProfileViewModel.updateUser(
                        drawable?.bitmap,
                        binding.editTextEmailAddressUpdate.editText?.text.toString(),
                        binding.editTextUserNameUpdate.editText?.text.toString(),
                        binding.editTextUserSurnameUpdate.editText?.text.toString(),
                        binding.editTextPhoneUserUpdate.editText?.text.toString(),
                        favourites,
                        binding.editTextPasswordUserUpdate.editText?.text.toString()
                    )
                }
                true
            }

            android.R.id.home -> {
                finish()
                true
            }else -> {
                super.onOptionsItemSelected(item)
            }
        }
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
            binding.imageViewUserProfile.setImageURI(data)
            drawable = binding.imageViewUserProfile.drawable as BitmapDrawable?
        }
    }

    //Abrimos la galeria
    private fun pickPhotoFormGallery() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        startForActivityGallery.launch(intent)
    }
}