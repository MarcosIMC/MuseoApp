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
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.museoapp.*
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
            binding.viewLoginContainer.setVisibility(View.GONE)
            val transaction = activity?.supportFragmentManager?.beginTransaction()
            transaction?.replace(R.id.nav_host_fragment_activity_main_view, UserProfileFragment.newInstance())
            transaction?.addToBackStack(null)
            transaction?.commit()
        }
    }

    private fun updateUI(){
        val transaction = activity?.supportFragmentManager?.beginTransaction()
        transaction?.replace(R.id.nav_host_fragment_activity_main_view, UserProfileFragment.newInstance())
        transaction?.disallowAddToBackStack()
        transaction?.commit()

        /*intent = Intent(activity, ProfileUserActivity::class.java)
        startActivity(intent)*/
    }
}