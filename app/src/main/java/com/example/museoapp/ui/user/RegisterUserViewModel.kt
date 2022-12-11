package com.example.museoapp.ui.user

import android.text.TextUtils
import androidx.lifecycle.ViewModel
import com.example.museoapp.R
import com.example.museoapp.databinding.ActivityRegisterUserBinding

class RegisterUserViewModel: ViewModel() {
    fun updateUI(registerUserActivity: RegisterUserActivity) {
        registerUserActivity.finish()
    }

    fun checkLabels(
        binding: ActivityRegisterUserBinding,
        registerUserActivity: RegisterUserActivity
    ): Boolean {
        var isValid = true
        if (TextUtils.isEmpty(binding.editTextMailRegister.editText?.text.toString())){
            binding.editTextMailRegister.error = registerUserActivity.getString(R.string.error_email_user)
            isValid = false
        }else {
            binding.editTextMailRegister.error = null
        }

        if (TextUtils.isEmpty(binding.editTextPasswordRegister.editText?.text.toString())){
            binding.editTextPasswordRegister.error = registerUserActivity.getString(R.string.error_password_user)
            isValid = false
        } else {
            binding.editTextPasswordRegister.error = null
        }

        if (TextUtils.isEmpty(binding.editTextTextNameRegister.editText?.text.toString())){
            binding.editTextTextNameRegister.error = registerUserActivity.getString(R.string.error_name_user)
            isValid = false
        } else {
            binding.editTextTextNameRegister.error = null
        }

        if (TextUtils.isEmpty(binding.editTextTextSurname.editText?.text.toString())){
            binding.editTextTextSurname.error = registerUserActivity.getString(R.string.error_surname_user)
            isValid = false
        } else {
            binding.editTextTextSurname.error = null
        }

        if (TextUtils.isEmpty(binding.editTextPhoneRegister.editText?.text.toString())){
            binding.editTextPhoneRegister.error = registerUserActivity.getString(R.string.error_tlf_user)
            isValid = false
        }else {
            binding.editTextPhoneRegister.error = null
        }

        return isValid
    }
}