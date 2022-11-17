package com.example.museoapp.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.museoapp.DetailItemActivity
import com.example.museoapp.ViewModel.GalleryViewModel
import com.example.museoapp.databinding.FragmentHomeBinding
import com.example.museoapp.model.GalleryModel
import com.example.museoapp.model.GalleryModelSerializable
import com.example.museoapp.model.adapter.ItemAdapter

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
        val homeViewModel =
        ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        galleryViweModel.galleriesObjects.observe(viewLifecycleOwner){ it ->
            if (it != null){
                val recyclerView = binding.recyclerView
                var adapter = ItemAdapter(this, it)
                recyclerView.adapter = adapter
                adapter.setOnItemClickListener(object : ItemAdapter.onItemClickListener{
                    override fun onItemClick(position: Int) {
                        val item = GalleryModelSerializable(it[position])
                        val intent = Intent(activity, DetailItemActivity::class.java)
                        intent.putExtra("id_item", item)
                        activity!!.startActivity(intent)
                    }
                })
                recyclerView.setHasFixedSize(true)
            }
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