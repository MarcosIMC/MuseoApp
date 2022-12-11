package com.example.museoapp.ui.user

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.museoapp.*
import com.example.museoapp.databinding.FragmentUserLoginBinding
import com.example.museoapp.ui.dialog.RestoreDialogFragment

class UserFragment : Fragment() {
    companion object {
        fun newInstance() = UserFragment()
    }
    private var _binding: FragmentUserLoginBinding? = null
    var userViewModel: UserViewModel? = null
    lateinit var intent : Intent
    // This property is only valid between onCreateView and
    // onDestroyView.
    private var isAdmin: Boolean = false
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
            if (userViewModel!!.checkLabels(binding, this)){
                userViewModel!!.signInWithEmail(binding.textEmail.editText?.text.toString(), binding.textPassword.editText?.text.toString())
            }
        }
        _binding!!.buttonForgotPassword.setOnClickListener {
            val fragmentManager = getFragmentManager()
            val newFragment = RestoreDialogFragment()
            if (true) {
                // The device is using a large layout, so show the fragment as a dialog
                newFragment.show(fragmentManager!!, "dialog")
            } else {
                // The device is smaller, so show the fragment fullscreen
                val transaction = fragmentManager?.beginTransaction()
                // For a little polish, specify a transition animation
                transaction?.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                // To make it fullscreen, use the 'content' root view as the container
                // for the fragment, which is always the root view for the activity
                transaction
                    ?.add(android.R.id.content, newFragment)
                    ?.addToBackStack(null)
                    ?.commit()
            }

        }
        _binding!!.buttonSignup.setOnClickListener { userViewModel!!.launchSignUp(activity) }

        userViewModel!!.userFirebase.observe(viewLifecycleOwner, Observer { currentUser ->
            if (currentUser != null && userViewModel!!.checkLogged()){
                userViewModel!!.isAdmin()
            }
        })

        userViewModel!!.isAdmin.observe(viewLifecycleOwner, Observer {
            isAdmin = false

            if (it == true) {
                isAdmin = true
            }
            updateUI()
        })

        userViewModel!!.error.observe(viewLifecycleOwner, Observer { errorMessage ->
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
        if(userViewModel!!.checkLogged() && !isAdmin){
            Navigation.findNavController(view!!).navigate(R.id.userProfileFragment)
        }else if (userViewModel!!.checkLogged() && isAdmin) {
            Navigation.findNavController(view!!).navigate(R.id.adminMainActivity)
        }
    }

    private fun updateUI(){
        if (userViewModel!!.checkLogged() && !isAdmin) {
            Navigation.findNavController(view!!).navigate(R.id.userProfileFragment)
        }else if (userViewModel!!.checkLogged() && isAdmin) {
            Navigation.findNavController(view!!).navigate(R.id.adminMainActivity)
        }
    }
}