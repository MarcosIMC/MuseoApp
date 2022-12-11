package com.example.museoapp.ui.dialog

import android.app.Dialog
import android.content.ContentValues.TAG
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.example.museoapp.R
import com.example.museoapp.databinding.DialogRestorePasswordBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class RestoreDialogFragment : DialogFragment() {

    private var _binding : DialogRestorePasswordBinding ? = null
    private val binding get() = _binding!!
    private val restoreDialogViewModel : RestoreDialogViewModel by viewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = DialogRestorePasswordBinding.inflate(layoutInflater)

        val dialog = MaterialAlertDialogBuilder(
            requireActivity()
        ).apply {
            setView(binding.root)

            setPositiveButton(R.string.send_email,
                DialogInterface.OnClickListener { dialog, id ->
                    Log.i(TAG, "Valor del email: " + binding.emailRestore.text.toString())
                    restoreDialogViewModel.sendEmail(binding.emailRestore.text.toString())
            }).setNegativeButton(R.string.cancel_restore,
                    DialogInterface.OnClickListener { dialog, id ->
                        getDialog()?.cancel()
            })

        }.create()

        return dialog
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}