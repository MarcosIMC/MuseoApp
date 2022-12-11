package com.example.museoapp.ui.user

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.museoapp.R
import com.example.museoapp.ViewModel.SignUpViewModel
import com.example.museoapp.databinding.ActivityRegisterUserBinding

class RegisterUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterUserBinding
    private val signUpViewModel : SignUpViewModel by viewModels()
    private val registerUserViewModel : RegisterUserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_user)

        binding = ActivityRegisterUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Habilitamos la flecha para volver atrás (Parent Activity)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        signUpViewModel.userFirebase.observe(this, Observer { currentUser ->
            if (currentUser != null){
                registerUserViewModel.updateUI(this)
            }
        })

        signUpViewModel.error.observe(this, Observer { errorMessage ->
            Toast.makeText(applicationContext, errorMessage, Toast.LENGTH_SHORT).show()
        })

        binding.buttonRegisterUser.setOnClickListener {
            if (registerUserViewModel.checkLabels(binding, this)){
                val email = binding.editTextMailRegister.editText?.text.toString()
                val password = binding.editTextPasswordRegister.editText?.text.toString()
                val name = binding.editTextTextNameRegister.editText?.text.toString()
                val surname = binding.editTextTextSurname.editText?.text.toString()
                val tlf = binding.editTextPhoneRegister.editText?.text.toString().toLong()

                signUpViewModel.createUser(email, password, name, surname, tlf)
            }
        }
    }
}