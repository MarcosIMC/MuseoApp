package com.example.museoapp.ui.user

import android.content.ContentValues.TAG
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.museoapp.ViewModel.UserDataViewModel
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
    private val userViewModel : UserDataViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val currentUser = auth?.currentUser
        _binding = FragmentUserProfileBinding.inflate(layoutInflater)
        val root: View = binding.root

        userViewModel.userDatas.observe(viewLifecycleOwner) {
            if (it != null && currentUser != null){
                binding.textViewName.text = currentUser.displayName
                binding.textViewEmail.text = currentUser.email
                binding.textViewSurname.text = it.surname
                binding.textViewTlf.text = it.tlf.toString()
            }
        }

        //return inflater.inflate(R.layout.fragment_user_profile, container, false)
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(UserProfileViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onStart() {
        super.onStart()
        Log.i(TAG, "Valor de la clave usuario: " + auth?.currentUser!!.uid)
        userViewModel.getUserDatas(auth?.currentUser!!.uid)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}