package com.example.museoapp.model.FireBase

import android.content.ContentValues.TAG
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.museoapp.LoginActivity
import com.example.museoapp.model.GalleryModel
import com.example.museoapp.ui.user.RegisterUserActivity
import com.example.museoapp.model.UserModel
import com.example.museoapp.ui.UpdateForm.UpdateProfileFormActivity
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.*

class Auth() {
    private lateinit var auth: FirebaseAuth
    private var firebaseDB = FireBase()
    private var myRef = firebaseDB.getRefDB()
    private var storage = Storage()
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
        image: Uri?,
        gallery: MutableMap<String, Boolean>,
        activity: RegisterUserActivity,
        userFirebase: MutableLiveData<FirebaseUser?>,
        error: MutableLiveData<String?>
    ){
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success")
                    updateNewUserData(auth.currentUser, name, surname, tlf, image, gallery,  activity, userFirebase, error)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    error.value = "createUserWithEmail:failure"
                    userFirebase.value = null
                }
            }
    }

    fun updateUserDataWithPass(
        email: String,
        password: String,
        name: String,
        surname: String,
        tlf: Long,
        gallery: MutableMap<String, Boolean>,
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
                updateUserData(auth.currentUser!!.uid, name, surname, tlf, image, gallery, 0, userFirebase, error)

                if (userFirebase.value != null) {
                    if (!auth.currentUser!!.email.equals(email)){
                        updateEmail(email, error)
                    }
                    updatePassword(password, error)

                    val credential = EmailAuthProvider.getCredential(email, password)
                    auth.currentUser!!.reauthenticate(credential)
                }else {
                    error.value = "An error ocurred when try update user."
                }
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

    private fun updateEmail(email: String, error: MutableLiveData<String?>) {
        auth.currentUser!!.updateEmail(email).addOnCompleteListener { task ->
            if (task.isSuccessful){
                Log.i(TAG, "User mail address updated.")
            }
        }
    }

    private fun updateUserData(
        uid: String,
        name: String,
        surname: String,
        tlf: Long,
        image: Bitmap?,
        gallery: MutableMap<String, Boolean>,
        role: Int,
        userFirebase: MutableLiveData<FirebaseUser?>,
        error: MutableLiveData<String?>
    ) {
        if (image != null){
            storage.uploadFile(Auth(), uid, name, surname, tlf, image, gallery, role, userFirebase, error)
        }
        writeNewUser(uid, name, surname, tlf, null, gallery, role, userFirebase, error)
    }

    private fun updateNewUserData(
        currentUser: FirebaseUser?,
        name: String,
        surname: String,
        tlf: Long,
        image: Uri?,
        gallery: MutableMap<String, Boolean>,
        activity: RegisterUserActivity,
        userFirebase: MutableLiveData<FirebaseUser?>,
        error: MutableLiveData<String?>
    ) {
        val profileUserUpdates = userProfileChangeRequest {
            displayName = name
            //photoUri = Uri.parse(image)
        }

        currentUser!!.updateProfile(profileUserUpdates).addOnCompleteListener(activity){ task ->
            if (task.isSuccessful){
                Log.d(TAG, "User profile updated")
                writeNewUser(currentUser.uid, name, surname, tlf, null, gallery,0, userFirebase, error)
            }else{
                error.value = "Register failed."
                userFirebase.value = null
            }
        }
    }

    fun writeNewUser(
        userId: String,
        name: String,
        surname: String,
        tlf: Long,
        image: String?,
        gallery: MutableMap<String, Boolean>,
        role: Int,
        userFirebase: MutableLiveData<FirebaseUser?>,
        error: MutableLiveData<String?>
    ) {
        Log.i(TAG, "Valor de image: " + image)
        val newUser = UserModel(name, surname, image, tlf, gallery, role)
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