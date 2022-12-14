package com.example.museoapp.ui.user

import android.content.ContentValues.TAG
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.example.museoapp.R
import com.example.museoapp.ViewModel.UserDataViewModel
import com.example.museoapp.databinding.FragmentUserProfileBinding
import com.example.museoapp.model.FireBase.Auth
import com.example.museoapp.model.adapter.ItemAdapter
import com.example.museoapp.ui.home.HomeViewModel

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
        val homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        userViewModel.userDatas.observe(viewLifecycleOwner) {
            if (it != null && currentUser != null){
                userProfileViewModel!!.setDatas(binding, activity, it, currentUser)

                if (it.gallery != null){
                    userProfileViewModel!!.loadFavourites(it.gallery)
                }
            }
        }

        userProfileViewModel!!.galleriesFavouritesObjects.observe(viewLifecycleOwner) {
            if (it != null) {
                //elementsGallery = it
                val recyclerView = binding.recyclerViewFavourites
                val adapter = ItemAdapter(context!!, it)
                recyclerView.adapter = adapter
                adapter.setOnItemClickListener(object : ItemAdapter.onItemClickListener{
                    override fun onItemClick(position: Int) {
                        homeViewModel.itemClicked(position, it, activity)
                    }
                })
                recyclerView.setHasFixedSize(true)
            }
        }

        //return inflater.inflate(R.layout.fragment_user_profile, container, true)
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
                Navigation.findNavController(view!!).navigate(R.id.updateProfileFormActivity)
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
        Log.i(TAG, "Dentro del destroy")
        _binding = null
    }

    private fun updateLogout(){
        if (auth?.currentUser == null){
            Navigation.findNavController(view!!).navigate(R.id.navigation_user)
        }
    }
}