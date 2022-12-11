package com.example.museoapp.ui.dialog

import androidx.lifecycle.ViewModel
import com.example.museoapp.model.FireBase.Auth

class RestoreDialogViewModel : ViewModel() {
    fun sendEmail(email: String) {
        Auth().restorePassword(email)
    }
}