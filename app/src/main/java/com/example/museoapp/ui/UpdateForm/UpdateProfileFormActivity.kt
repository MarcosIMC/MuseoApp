package com.example.museoapp.ui.UpdateForm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.museoapp.R

class UpdateProfileFormActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_profile_form)

        //Habilitamos la flecha para volver atrás (Parent Activity)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

    }
}