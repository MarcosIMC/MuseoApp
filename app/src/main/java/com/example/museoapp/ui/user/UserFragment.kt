package com.example.museoapp.ui.user

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.museoapp.MainViewActivity
import com.example.museoapp.ProfileUserActivity
import com.example.museoapp.RegisterUserActivity
import com.example.museoapp.ViewModel.LoginViewModel
import com.example.museoapp.databinding.FragmentUserLoginBinding

class UserFragment : Fragment() {

    private var _binding: FragmentUserLoginBinding? = null
    private val loginViewModel: UserViewModel by viewModels()
    lateinit var intent : Intent
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val userViewModel =
            ViewModelProvider(this).get(UserViewModel::class.java)

        _binding = FragmentUserLoginBinding.inflate(inflater, container, false)
        val root: View = binding.root

        _binding!!.buttonLogin.setOnClickListener { loginViewModel.signInWithEmail(binding.textEmail.text.toString(), binding.textPassword.text.toString()) }
        _binding!!.buttonSignup.setOnClickListener { intent = Intent(activity, RegisterUserActivity::class.java)
            startActivity(intent) }

        loginViewModel.userFirebase.observe(viewLifecycleOwner, Observer { currentUser ->
            if (currentUser != null){
                updateUI()
            }
        })

        loginViewModel.error.observe(viewLifecycleOwner, Observer { errorMessage ->
            println("MENSAJE: "+errorMessage)
            Toast.makeText(activity, errorMessage,
                Toast.LENGTH_SHORT).show()
        })

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    public override fun onStart() {
        super.onStart()
        if(loginViewModel.checkLogged()){
            intent = Intent(activity, ProfileUserActivity::class.java)
            startActivity(intent)
        }
    }

    private fun updateUI(){
        intent = Intent(activity, ProfileUserActivity::class.java)
        startActivity(intent)
    }
}