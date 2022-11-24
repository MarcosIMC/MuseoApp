package com.example.museoapp.ui.UpdateForm

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
import androidx.core.text.set
import com.example.museoapp.R
import com.example.museoapp.ViewModel.UpdateProfileViewModel
import com.example.museoapp.ViewModel.UserDataViewModel
import com.example.museoapp.databinding.ActivityUpdateProfileFormBinding
import com.example.museoapp.model.FireBase.Auth
import com.example.museoapp.model.UserModel
import com.google.firebase.auth.FirebaseUser

class UpdateProfileFormActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUpdateProfileFormBinding
    private val updateProfileViewModel: UpdateProfileViewModel by viewModels()
    private val userViewModel : UserDataViewModel by viewModels()
    private var authObj = Auth()
    private var auth = authObj.getAuth()
    private var drawable : BitmapDrawable? = null

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
                loadLabels(currentUser, it)
            }
        }
    }



    private fun loadLabels(currentUser: FirebaseUser, userModel: UserModel) {
        binding.editTextEmailAddressUpdate.setText(currentUser.email)
        binding.editTextUserNameUpdate.setText(userModel.name)
        binding.editTextUserSurnameUpdate.setText(userModel.surname)
        binding.editTextPhoneUserUpdate.setText(userModel.tlf.toString())
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
                if (checkLabels() && checkPassword()) {
                    updateProfileViewModel.updateUser(
                        drawable?.bitmap,
                        binding.editTextEmailAddressUpdate,
                        binding.editTextUserNameUpdate,
                        binding.editTextUserSurnameUpdate,
                        binding.editTextPhoneUserUpdate,
                        binding.editTextPasswordUserUpdate
                    )
                }
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

    //Comprobamos si las pass son diferentes
    private fun checkPassword(): Boolean {
        if (binding.editTextPasswordUserUpdate.text.toString().equals(binding.editTextPasswordRepeatUserUpdate.text.toString())) {
            return true
        }
        binding.editTextPasswordUserUpdate.error = getString(R.string.error_password_different)
        binding.editTextPasswordRepeatUserUpdate.error = getString(R.string.error_password_different)
        return false
    }

    //Comprobamos todos los campos
    private fun checkLabels() : Boolean {
        var isValid = true
        if (TextUtils.isEmpty(binding.editTextEmailAddressUpdate.text.toString())){
            binding.editTextEmailAddressUpdate.error = getString(R.string.error_email_user)
            isValid = false
        }else{
            binding.editTextEmailAddressUpdate.error = null
        }


        if (TextUtils.isEmpty(binding.editTextUserNameUpdate.text.toString())){
            binding.editTextUserNameUpdate.error = getString(R.string.error_name_user)
            isValid = false
        }else{
            binding.editTextUserNameUpdate.error = null
        }

        if (TextUtils.isEmpty(binding.editTextUserSurnameUpdate.text.toString())){
            binding.editTextUserSurnameUpdate.error = getString(R.string.error_surname_user)
            isValid = false
        }else{
            binding.editTextUserSurnameUpdate.error = null
        }

        if (TextUtils.isEmpty(binding.editTextPhoneUserUpdate.text.toString())){
            binding.editTextPhoneUserUpdate.error = getString(R.string.error_tlf_user)
            isValid = false
        }else{
            binding.editTextPhoneUserUpdate.error = null
        }

        if (TextUtils.isEmpty(binding.editTextPasswordUserUpdate.text.toString())){
            binding.editTextPasswordUserUpdate.error = getString(R.string.error_password_user)
            isValid = false
        }else{
            binding.editTextPasswordUserUpdate.error = null
        }

        if (TextUtils.isEmpty(binding.editTextPasswordRepeatUserUpdate.text.toString())){
            binding.editTextPasswordRepeatUserUpdate.error = getString(R.string.error_password_user)
            isValid = false
        }else{
            binding.editTextPasswordRepeatUserUpdate.error = null
        }

        return isValid
    }
}