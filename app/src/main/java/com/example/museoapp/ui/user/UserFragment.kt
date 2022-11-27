package com.example.museoapp.ui.user

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.museoapp.*
import com.example.museoapp.databinding.FragmentUserLoginBinding

class UserFragment : Fragment() {
    companion object {
        fun newInstance() = UserFragment()
    }
    private var _binding: FragmentUserLoginBinding? = null
    var userViewModel: UserViewModel? = null
    lateinit var intent : Intent
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        _binding = FragmentUserLoginBinding.inflate(inflater, container, false)
        val root: View = binding.root

        _binding!!.buttonLogin.setOnClickListener {
            if (checkLabels()){
                userViewModel!!.signInWithEmail(binding.textEmail.editText?.text.toString(), binding.textPassword.editText?.text.toString())
            }
        }
        _binding!!.buttonSignup.setOnClickListener { intent = Intent(activity, RegisterUserActivity::class.java)
            startActivity(intent) }

        userViewModel!!.userFirebase.observe(viewLifecycleOwner, Observer { currentUser ->
            if (currentUser != null){
                updateUI()
            }
        })

        userViewModel!!.error.observe(viewLifecycleOwner, Observer { errorMessage ->
            println("MENSAJE: "+errorMessage)
            Toast.makeText(activity, errorMessage,
                Toast.LENGTH_SHORT).show()
        })

        return root
    }

    private fun checkLabels(): Boolean {
        var isValid = true
        if (TextUtils.isEmpty(binding.textEmail.editText?.text.toString())){
            binding.textEmail.error = getString(R.string.error_email_user)
            isValid = false
        }else {
            binding.textEmail.error = null
        }

        if (TextUtils.isEmpty(binding.textPassword.editText?.text.toString())){
            binding.textPassword.error = getString(R.string.error_password_user)
            isValid = false
        }else {
            binding.textPassword.error = null
        }
        return  isValid
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    public override fun onStart() {
        super.onStart()
        if(userViewModel!!.checkLogged()){
            Navigation.findNavController(view!!).navigate(R.id.userProfileFragment)
        }
    }

    private fun updateUI(){
        Navigation.findNavController(view!!).navigate(R.id.userProfileFragment)
    }
}