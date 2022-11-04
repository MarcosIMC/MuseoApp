package com.example.museoapp.model.FireBase

import android.content.ContentValues.TAG
import android.net.Uri
import android.util.Log
import com.example.museoapp.LoginActivity
import com.example.museoapp.RegisterUserActivity
import com.example.museoapp.model.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class Auth {
    lateinit var auth: FirebaseAuth
    var myRef: DatabaseReference? = null
    var user: FirebaseUser? = null
    var errorMessage: String? = ""

    @JvmName("getAuth1")
    fun getAuth(): FirebaseAuth{
        auth = Firebase.auth
        return auth
    }

    @JvmName("getUser1")
    fun getUser(): FirebaseUser? {
        return user
    }

    fun getError(): String? {
        return errorMessage
    }

    fun initializeDatabaseRef() {
        myRef = FirebaseDatabase.getInstance("https://museo-app-4246f-default-rtdb.europe-west1.firebasedatabase.app/").getReference()

    }

    fun checkUserSigned(auth: FirebaseAuth): Boolean{
        val currentUser = auth.currentUser

        if(currentUser != null){
            return true
        }
        return false
    }

    fun signInWithEmailAndPassword(email: String, password: String, activity: LoginActivity, auth: FirebaseAuth){
        auth.signInWithEmailAndPassword(email,
            password
        ).addOnCompleteListener(activity) { task ->
            if (task.isSuccessful){
                Log.d(TAG, "Inicio de sesión: success")
                user = this.auth.currentUser
            }else{
                Log.w(TAG, "signInWithEmail:failure", task.exception)
                errorMessage = "Authentication failed."
                user = null
            }
        }
    }

    fun createUserWithEmailAndPassword(email: String, password: String, auth: FirebaseAuth,
    name:String, surname:String, tlf:Long, image:String, activity: RegisterUserActivity){
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(activity) {task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success")
                    val user = auth.currentUser
                    updateUserData(user, name, surname, tlf, image, activity)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    return@addOnCompleteListener
                }
            }
    }

    private fun updateUserData(user: FirebaseUser?, name: String, surname: String, tlf: Long, image: String, activity: RegisterUserActivity) {
        val profileUserUpdates = userProfileChangeRequest {
            displayName = name
            photoUri = Uri.parse(image)
        }

        user!!.updateProfile(profileUserUpdates).addOnCompleteListener(activity){ task ->
            if (task.isSuccessful){
                Log.d(TAG, "User profile updated")
                writeNewUser(user.uid, name, surname, tlf, image, 0)
                //activity.updateUI(user)
            }else{
                errorMessage = "Register failed."
            }
        }
    }

    private fun writeNewUser(userId: String, name: String, surname: String, tlf: Long, image: String, role: Int) {
        val user = UserModel(name, surname, image, tlf, role)
        initializeDatabaseRef()
        println("USER UID: "+ userId)
        myRef?.child("users")?.child(userId)?.setValue(user)
    }
}