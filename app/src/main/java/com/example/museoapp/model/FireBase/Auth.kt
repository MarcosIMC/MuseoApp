package com.example.museoapp.model.FireBase

import android.content.ContentValues.TAG
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.museoapp.LoginActivity
import com.example.museoapp.ui.user.RegisterUserActivity
import com.example.museoapp.ui.UpdateForm.UpdateProfileFormActivity
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.ktx.Firebase

class Auth() {
    private lateinit var auth: FirebaseAuth
    private var firebaseDB = FireBase()
    private var myRef = firebaseDB.getRefDB()
    private var userFirebase = UserFireBase()

    init {
        auth = Firebase.auth
    }

    fun getAuth():FirebaseAuth?{
        return auth
    }

    fun isAdmin(isAdmin: MutableLiveData<Boolean?>) {
        myRef?.child("users")?.child(auth.currentUser!!.uid)?.child("role")?.get()
            ?.addOnSuccessListener {
                Log.i(TAG, "Valor de is admin: " + it.value)
                if (it.value.toString().toInt() == 1){
                    Log.i(TAG, "DEntro, cambia isAdmin")
                    isAdmin.value = true
                }else if (it.value.toString().toInt() == 0){
                    isAdmin.value = false
                }
            }?.addOnFailureListener {
                isAdmin.value = false
            }
    }

    fun checkUserSigned(): Boolean{
        val currentUser = auth.currentUser

        if(currentUser != null){
            return true
        }
        return false
    }

    fun signInWithEmailAndPassword(email: String, password: String, activity: LoginActivity, userFirebase: MutableLiveData<FirebaseUser?>, error: MutableLiveData<String?>){
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(activity) { task ->
            if (task.isSuccessful){
                Log.d(TAG, "Inicio de sesión: success")
                userFirebase.value = auth.currentUser
            }else{
                Log.w(TAG, "signInWithEmail:failure", task.exception)
                error.value = "Authentication failed."
                userFirebase.value = null
            }
        }
    }

    fun createUserWithEmailAndPassword(
        email: String,
        password: String,
        name: String,
        surname: String,
        tlf: Long,
        image: Uri?,
        gallery: MutableMap<String, Boolean>?,
        activity: RegisterUserActivity,
        userFirebase: MutableLiveData<FirebaseUser?>,
        error: MutableLiveData<String?>
    ){
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success")
                    this.userFirebase.updateNewUserData(auth.currentUser, name, surname, tlf, image,
                        gallery,  activity, userFirebase, error)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    error.value = "createUserWithEmail:failure"
                    userFirebase.value = null
                }
            }
    }

    fun updateAuthProfileDataWithPass(
        email: String,
        password: String,
        name: String,
        surname: String,
        tlf: Long,
        gallery: MutableMap<String, Boolean>?,
        image: Bitmap?,
        activity: UpdateProfileFormActivity,
        userFirebase: MutableLiveData<FirebaseUser?>,
        error: MutableLiveData<String?>
    ) {
        val profileUserUpdates = userProfileChangeRequest {
            displayName = name
            //photoUri = Uri.parse(image)
        }

        auth.currentUser!!.updateProfile(profileUserUpdates).addOnCompleteListener(activity) { task ->
            if (task.isSuccessful){
                this.userFirebase.updateUserData(auth.currentUser!!.uid, name, surname, tlf, image, gallery, 0, userFirebase, error, auth.currentUser)
                /*if (!auth.currentUser!!.email.equals(email)){
                    updateEmail(email, error, password)
                }*/
                //updatePassword(password, error)
            }else{
                error.value = "Update failed."
                userFirebase.value = null
            }
        }
    }

    fun updatePassword(password: String, error: MutableLiveData<String?>) {
        auth.currentUser!!.updatePassword(password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d(TAG, "Password changed")
            }else {
                error.value = "Falied to update the password"
            }
        }
    }

    private fun updateEmail(email: String, error: MutableLiveData<String?>, password: String) {
        Log.i(TAG, "DEntro del updateEmail")
        if (auth.currentUser == null) {
            Log.i(TAG, "auth null")
        }
        auth.currentUser!!.updateEmail(email).addOnCompleteListener { task ->
            if (task.isSuccessful){
                val credential = EmailAuthProvider.getCredential(email, password)
                auth.currentUser!!.reauthenticate(credential)
                Log.i(TAG, "User mail address updated.")
            }
        }
    }

    fun logOut(){
        Firebase.auth.signOut()
    }

    fun restorePassword(email: String) {
        auth.sendPasswordResetEmail(email).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.i(TAG, "Email enviado")
            }
        }
    }
}