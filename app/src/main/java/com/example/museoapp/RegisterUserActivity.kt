package com.example.museoapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.museoapp.model.FireBase.Auth
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RegisterUserActivity : AppCompatActivity() {
    private var authObj = Auth()
    private var auth = authObj.getAuth()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_user)

        auth = Firebase.auth

        val btnRegister = findViewById<Button>(R.id.buttonRegisterUser)
        btnRegister.setOnClickListener {
            var name = findViewById<EditText>(R.id.editTextTextNameRegister)
            var surname = findViewById<EditText>(R.id.editTextTextSurname)
            var email = findViewById<EditText>(R.id.editTextMailRegister)
            var password = findViewById<EditText>(R.id.editTextPasswordRegister)
            var tlf = findViewById<EditText>(R.id.editTextPhoneRegister)

            authObj.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString(), auth,
            name.getText().toString(), surname.getText().toString(), tlf.getText().toString().toLong(), "", this)
        }
    }

    public fun updateUI(user: FirebaseUser?) {
        if(user != null){
            var intent = Intent(this, ProfileUserActivity::class.java)
            startActivity(intent)
        }
    }
}