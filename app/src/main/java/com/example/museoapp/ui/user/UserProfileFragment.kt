package com.example.museoapp.ui.user

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.museoapp.R
import com.example.museoapp.ViewModel.UserDataViewModel
import com.example.museoapp.databinding.FragmentUserProfileBinding
import com.example.museoapp.model.FireBase.Auth
import com.example.museoapp.ui.DetailItem.DetailItemActivity
import com.example.museoapp.ui.UpdateForm.UpdateProfileFormActivity

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
    private var userProfileViewModel : UserProfileViewModel? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        val currentUser = auth?.currentUser
        _binding = FragmentUserProfileBinding.inflate(layoutInflater)
        val root: View = binding.root
        userProfileViewModel = ViewModelProvider(this).get(UserProfileViewModel::class.java)
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_app_bar_user_profile, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.log_out_button -> {
                userProfileViewModel?.log_out()
                updateLogout()

                true
            }

            R.id.update_button -> {
                val intent = Intent(activity, UpdateProfileFormActivity::class.java)
                activity!!.startActivity(intent)
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(UserProfileViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onStart() {
        super.onStart()
        userViewModel.getUserDatas(auth?.currentUser!!.uid)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun updateLogout(){
        if (auth?.currentUser == null){
            binding.userProfileFragment.setVisibility(View.GONE)
            val transaction = activity?.supportFragmentManager?.beginTransaction()
            transaction?.replace(R.id.nav_host_fragment_activity_main_view, UserFragment.newInstance())
            transaction?.addToBackStack(null)
            transaction?.commit()
        }
    }
}