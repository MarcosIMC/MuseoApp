package com.example.museoapp.ui.UpdateForm

import android.graphics.Bitmap
import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation
import com.example.museoapp.R
import com.example.museoapp.databinding.ActivityUpdateProfileFormBinding
import com.example.museoapp.model.FireBase.Auth
import com.example.museoapp.model.UserModel
import com.google.firebase.auth.FirebaseUser

class UpdateProfileViewModel : ViewModel(){
    private var authObj = Auth()
    val userFirebase = MutableLiveData<FirebaseUser?>()
    val error = MutableLiveData<String?>()

    fun updateUser(
        image: Bitmap?,
        email: String,
        name: String,
        surname: String,
        phone: String,
        gallery: MutableMap<String, Boolean>?,
        password: String
    ) {
        authObj.updateAuthProfileDataWithPass(email, password, name,
        surname, phone.toLong(), gallery, image, UpdateProfileFormActivity(), userFirebase, error)
    }

    fun updateUI(updateProfileFormActivity: UpdateProfileFormActivity) {
        updateProfileFormActivity.finish()
    }

    fun loadLabels(
        currentUser: FirebaseUser,
        userModel: UserModel,
        binding: ActivityUpdateProfileFormBinding
    ) {
        binding.editTextEmailAddressUpdate.editText?.setText(currentUser.email)
        binding.editTextUserNameUpdate.editText?.setText(userModel.name)
        binding.editTextUserSurnameUpdate.editText?.setText(userModel.surname)
        binding.editTextPhoneUserUpdate.editText?.setText(userModel.tlf.toString())
    }

    //Comprobamos todos los campos
    fun checkLabels(
        binding: ActivityUpdateProfileFormBinding,
        updateProfileFormActivity: UpdateProfileFormActivity
    ): Boolean {
        var isValid = true
        if (TextUtils.isEmpty(binding.editTextEmailAddressUpdate.editText?.text.toString())){
            binding.editTextEmailAddressUpdate.error = updateProfileFormActivity.getString(R.string.error_email_user)
            isValid = false
        }else{
            binding.editTextEmailAddressUpdate.error = null
        }


        if (TextUtils.isEmpty(binding.editTextUserNameUpdate.editText?.text.toString())){
            binding.editTextUserNameUpdate.error = updateProfileFormActivity.getString(R.string.error_name_user)
            isValid = false
        }else{
            binding.editTextUserNameUpdate.error = null
        }

        if (TextUtils.isEmpty(binding.editTextUserSurnameUpdate.editText?.text.toString())){
            binding.editTextUserSurnameUpdate.error = updateProfileFormActivity.getString(R.string.error_surname_user)
            isValid = false
        }else{
            binding.editTextUserSurnameUpdate.error = null
        }

        if (TextUtils.isEmpty(binding.editTextPhoneUserUpdate.editText?.text.toString())){
            binding.editTextPhoneUserUpdate.error = updateProfileFormActivity.getString(R.string.error_tlf_user)
            isValid = false
        }else{
            binding.editTextPhoneUserUpdate.error = null
        }

        if (TextUtils.isEmpty(binding.editTextPasswordUserUpdate.editText?.text.toString())){
            binding.editTextPasswordUserUpdate.error = updateProfileFormActivity.getString(R.string.error_password_user)
            isValid = false
        }else{
            binding.editTextPasswordUserUpdate.error = null
        }

        if (TextUtils.isEmpty(binding.editTextPasswordRepeatUserUpdate.editText?.text.toString())){
            binding.editTextPasswordRepeatUserUpdate.error = updateProfileFormActivity.getString(R.string.error_password_user)
            isValid = false
        }else{
            binding.editTextPasswordRepeatUserUpdate.error = null
        }

        return isValid
    }

    //Comprobamos si las pass son diferentes
    fun checkPassword(
        binding: ActivityUpdateProfileFormBinding,
        updateProfileFormActivity: UpdateProfileFormActivity
    ): Boolean {
        if (binding.editTextPasswordUserUpdate.editText?.text.toString().equals(binding.editTextPasswordRepeatUserUpdate.editText?.text.toString())) {
            return true
        }
        binding.editTextPasswordUserUpdate.error = updateProfileFormActivity.getString(R.string.error_password_different)
        binding.editTextPasswordRepeatUserUpdate.error = updateProfileFormActivity.getString(R.string.error_password_different)
        return false
    }
}