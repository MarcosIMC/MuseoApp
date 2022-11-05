package com.example.museoapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.museoapp.ViewModel.SignUpViewModel
import com.example.museoapp.databinding.ActivityLoginBinding
import com.example.museoapp.databinding.ActivityRegisterUserBinding
import com.example.museoapp.model.FireBase.Auth
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RegisterUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterUserBinding
    private val signUpViewModel : SignUpViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_user)

        binding = ActivityRegisterUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        signUpViewModel.userFirebase.observe(this, Observer { currentUser ->
            if (currentUser != null){
                updateUI()
            }
        })

        signUpViewModel.error.observe(this, Observer { errorMessage ->
            Toast.makeText(applicationContext, errorMessage, Toast.LENGTH_SHORT).show()
        })

        binding.buttonRegisterUser.setOnClickListener {
            var email = binding.editTextMailRegister.text.toString()
            var password = binding.editTextPasswordRegister.text.toString()
            var name = binding.editTextTextNameRegister.text.toString()
            var surname = binding.editTextTextSurname.text.toString()
            var tlf = binding.editTextPhoneRegister.text.toString().toLong()

            signUpViewModel.createUser(email, password, name, surname, tlf)
        }
    }

    private fun updateUI() {
        intent = Intent(this, ProfileUserActivity::class.java)
        startActivity(intent)
    }
}