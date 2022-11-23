package com.example.museoapp.model.FireBase

import android.content.ContentValues.TAG
import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.museoapp.LoginActivity
import com.example.museoapp.ui.user.RegisterUserActivity
import com.example.museoapp.model.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.ktx.Firebase

class Auth() {
    private lateinit var auth: FirebaseAuth
    private var firebaseDB = FireBase()
    private var myRef = firebaseDB.getRefDB()
    //var myRef: DatabaseReference? = null

    init {
        auth = Firebase.auth
    }

    fun getAuth():FirebaseAuth?{
        return auth
    }

    /*fun initializeDatabaseRef() {
        myRef = FirebaseDatabase.getInstance("https://museo-app-4246f-default-rtdb.europe-west1.firebasedatabase.app/").getReference()
    }*/

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
        image: String,
        activity: RegisterUserActivity,
        userFirebase: MutableLiveData<FirebaseUser?>,
        error: MutableLiveData<String?>
    ){
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success")
                    updateUserData(auth.currentUser, name, surname, tlf, image, activity, userFirebase, error)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    error.value = "createUserWithEmail:failure"
                    userFirebase.value = null
                }
            }
    }

    private fun updateUserData(
        currentUser: FirebaseUser?,
        name: String,
        surname: String,
        tlf: Long,
        image: String,
        activity: RegisterUserActivity,
        userFirebase: MutableLiveData<FirebaseUser?>,
        error: MutableLiveData<String?>
    ) {
        val profileUserUpdates = userProfileChangeRequest {
            displayName = name
            photoUri = Uri.parse(image)
        }

        currentUser!!.updateProfile(profileUserUpdates).addOnCompleteListener(activity){ task ->
            if (task.isSuccessful){
                Log.d(TAG, "User profile updated")
                writeNewUser(currentUser.uid, name, surname, tlf, image, 0, userFirebase, error)
            }else{
                error.value = "Register failed."
                userFirebase.value = null
            }
        }
    }

    private fun writeNewUser(
        userId: String,
        name: String,
        surname: String,
        tlf: Long,
        image: String,
        role: Int,
        userFirebase: MutableLiveData<FirebaseUser?>,
        error: MutableLiveData<String?>
    ) {
        val newUser = UserModel(name, surname, image, tlf, role)
        //initializeDatabaseRef()
        println("USER UID: "+ userId)
        myRef?.child("users")?.child(userId)?.setValue(newUser)
            ?.addOnCompleteListener {
                userFirebase.value = auth.currentUser
            }
            ?.addOnFailureListener {
                error.value = "Creación fallida"
                userFirebase.value = null
            }
    }

    fun logOut(){
        Firebase.auth.signOut()
    }
}