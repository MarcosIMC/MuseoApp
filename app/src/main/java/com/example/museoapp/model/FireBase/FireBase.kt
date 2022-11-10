package com.example.museoapp.model.FireBase

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class FireBase {
    private var refDB: DatabaseReference? = null

    init {
        refDB = FirebaseDatabase.getInstance("https://museo-app-4246f-default-rtdb.europe-west1.firebasedatabase.app/").reference
    }

    fun getRefDB(): DatabaseReference? {
        return refDB
    }

}