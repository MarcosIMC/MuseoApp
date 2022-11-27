package com.example.museoapp.ui.user

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.museoapp.R
import com.example.museoapp.ViewModel.SignUpViewModel
import com.example.museoapp.databinding.ActivityRegisterUserBinding

class RegisterUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterUserBinding
    private val signUpViewModel : SignUpViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_user)

        binding = ActivityRegisterUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Habilitamos la flecha para volver atrás (Parent Activity)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        signUpViewModel.userFirebase.observe(this, Observer { currentUser ->
            if (currentUser != null){
                updateUI()
            }
        })

        signUpViewModel.error.observe(this, Observer { errorMessage ->
            Toast.makeText(applicationContext, errorMessage, Toast.LENGTH_SHORT).show()
        })

        binding.buttonRegisterUser.setOnClickListener {
            if (checkLabels()){
                val email = binding.editTextMailRegister.editText?.text.toString()
                val password = binding.editTextPasswordRegister.editText?.text.toString()
                val name = binding.editTextTextNameRegister.editText?.text.toString()
                val surname = binding.editTextTextSurname.editText?.text.toString()
                val tlf = binding.editTextPhoneRegister.editText?.text.toString().toLong()

                signUpViewModel.createUser(email, password, name, surname, tlf)
            }
        }
    }

    private fun checkLabels(): Boolean {
        var isValid = true
        if (TextUtils.isEmpty(binding.editTextMailRegister.editText?.text.toString())){
            binding.editTextMailRegister.error = getString(R.string.error_email_user)
            isValid = false
        }else {
            binding.editTextMailRegister.error = null
        }

        if (TextUtils.isEmpty(binding.editTextPasswordRegister.editText?.text.toString())){
            binding.editTextPasswordRegister.error = getString(R.string.error_password_user)
            isValid = false
        } else {
            binding.editTextPasswordRegister.error = null
        }

        if (TextUtils.isEmpty(binding.editTextTextNameRegister.editText?.text.toString())){
            binding.editTextTextNameRegister.error = getString(R.string.error_name_user)
            isValid = false
        } else {
            binding.editTextTextNameRegister.error = null
        }

        if (TextUtils.isEmpty(binding.editTextTextSurname.editText?.text.toString())){
            binding.editTextTextSurname.error = getString(R.string.error_surname_user)
            isValid = false
        } else {
            binding.editTextTextSurname.error = null
        }

        if (TextUtils.isEmpty(binding.editTextPhoneRegister.editText?.text.toString())){
            binding.editTextPhoneRegister.error = getString(R.string.error_tlf_user)
            isValid = false
        }else {
            binding.editTextPhoneRegister.error = null
        }

        return isValid
    }

    private fun updateUI() {
        intent = Intent(this, UserProfileFragment::class.java)
        startActivity(intent)
    }
}