package com.example.museoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.museoapp.model.FireBase.Auth
import com.example.museoapp.model.UserModel
import com.google.firebase.auth.FirebaseUser

class ProfileUserActivity : AppCompatActivity() {
    private lateinit var user: FirebaseUser
    private var authObj = Auth()
    private var auth = authObj.getAuth()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_user)

        val currentUser = auth?.currentUser

        val txtName = findViewById<TextView>(R.id.textViewName)
        val txtEmail = findViewById<TextView>(R.id.textViewEmail)
        if (currentUser != null) {
            txtName.setText(currentUser.displayName)
            txtEmail.setText(currentUser.email)
        }
    }

}