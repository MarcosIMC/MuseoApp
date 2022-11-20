package com.example.museoapp

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.museoapp.databinding.ActivityDetailItemBinding
import com.example.museoapp.databinding.FragmentUserProfileBinding
import com.example.museoapp.model.FireBase.Auth

class UserProfileFragment : Fragment() {

    companion object {
        fun newInstance() = UserProfileFragment()
    }

    private lateinit var viewModel: UserProfileViewModel
    private var authObj = Auth()
    private var auth = authObj.getAuth()
    private var _binding: FragmentUserProfileBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val currentUser = auth?.currentUser
        _binding = FragmentUserProfileBinding.inflate(layoutInflater)
        val root: View = binding.root

        if (currentUser != null) {
            binding.textViewName.text = currentUser.displayName
            binding.textViewEmail.text = currentUser.email
        }

        //return inflater.inflate(R.layout.fragment_user_profile, container, false)
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(UserProfileViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}