package com.example.museoapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.museoapp.model.FireBase.Auth
import com.example.museoapp.ViewModel.LoginViewModel
import com.example.museoapp.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loginViewModel.userFirebase.observe(this, Observer { currentUser ->
            if (currentUser != null){
                updateUI()
            }
        })

        loginViewModel.error.observe(this, Observer { errorMessage ->
            println("MENSAJE: "+errorMessage)
            Toast.makeText(applicationContext, errorMessage,
                Toast.LENGTH_SHORT).show()
        })

        binding.buttonLogin.setOnClickListener { loginViewModel.signInWithEmail(binding.textEmail.text.toString(), binding.textPassword.text.toString()) }
        binding.buttonSignup.setOnClickListener { intent = Intent(this, RegisterUserActivity::class.java)
            startActivity(intent) }
    }

    public override fun onStart() {
        super.onStart()

        if(loginViewModel.checkLogged()){
            intent = Intent(this, ProfileUserActivity::class.java)
            startActivity(intent)
        }
    }

    private fun updateUI(){
        intent = Intent(this, ProfileUserActivity::class.java)
        startActivity(intent)
    }

}