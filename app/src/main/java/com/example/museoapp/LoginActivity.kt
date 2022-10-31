package com.example.museoapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.museoapp.FireBase.Auth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {
    private var authObj = Auth()
    private var auth = authObj.getAuth()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = Firebase.auth
        val btnLogin = findViewById<Button>(R.id.button_login)
        btnLogin.setOnClickListener {
            var email = findViewById<EditText>(R.id.textEmail)
            var password = findViewById<EditText>(R.id.textPassword)
            authObj.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString(), this, auth)
        }

        val btnRegister = findViewById<Button>(R.id.button_signup)
        btnRegister.setOnClickListener {
            val intent = Intent(this, RegisterUserActivity::class.java)
            startActivity(intent)
        }
    }

    public override fun onStart() {
        super.onStart()

        if(authObj.checkUserSigned(auth)){
            val intent = Intent(this, ProfileUserActivity::class.java)
            intent.putExtra("User", auth.currentUser)
            startActivity(intent)
        }
    }

    public fun updateUI(user: FirebaseUser?){
        if (user != null){
            val intentLogin = Intent(this, ProfileUserActivity::class.java)
            startActivity(intentLogin)
        }else{
            val intentLogin = Intent(this, LoginActivity::class.java)
            startActivity(intentLogin)
        }
    }

}