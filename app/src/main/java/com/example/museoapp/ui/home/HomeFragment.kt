package com.example.museoapp.ui.home

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.museoapp.ViewModel.GalleryViewModel
import com.example.museoapp.databinding.FragmentHomeBinding
import com.example.museoapp.model.GalleryModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val galleryViweModel : GalleryViewModel by viewModels()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        galleryViweModel.galleriesObjects.observe(viewLifecycleOwner){
            if (it != null){
                for (item in it){

                    Log.i(TAG, "VALUE : " + item)
                    Log.i(TAG, "NAME: " + item.name)
                    Log.i(TAG, "SORT_DESCRIPTION: " + item.sort_description)
                }
            }
        }

        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textHome
        homeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onStart() {
        super.onStart()
        galleryViweModel.getAllElements()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}