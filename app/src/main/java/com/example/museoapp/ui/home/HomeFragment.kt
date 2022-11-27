package com.example.museoapp.ui.home

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.museoapp.R
import com.example.museoapp.ViewModel.GalleryViewModel
import com.example.museoapp.databinding.FragmentHomeBinding
import com.example.museoapp.model.GalleryModel
import com.example.museoapp.model.adapter.CarouselAdapter
import com.example.museoapp.model.adapter.ItemAdapter
import com.jackandphantom.carouselrecyclerview.CarouselLayoutManager
import com.jackandphantom.carouselrecyclerview.CarouselRecyclerview

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val galleryViweModel : GalleryViewModel by viewModels()
    private lateinit var elementsGallery : MutableList<GalleryModel>
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(false)
        super.onCreate(savedInstanceState)

        val homeViewModel =
        ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        galleryViweModel.galleriesObjects.observe(viewLifecycleOwner){ it ->
            if (it != null){
                elementsGallery = it
                val recyclerView = binding.recyclerView
                val adapter = ItemAdapter(this, it)
                recyclerView.adapter = adapter
                adapter.setOnItemClickListener(object : ItemAdapter.onItemClickListener{
                    override fun onItemClick(position: Int) {
                        homeViewModel.itemClicked(position, it, activity)
                    }
                })
                recyclerView.setHasFixedSize(true)
            }
        }

        galleryViweModel.galleriesImage.observe(viewLifecycleOwner){
            if (it != null){
                val adapterCarousel = CarouselAdapter(it)
                binding.recycler.apply {
                    this.adapter = adapterCarousel
                    setInfinite(true)
                    set3DItem(true)
                    setAlpha(true)
                    setIntervalRatio(0.7f)
                }
            }
        }

        return root
    }

    override fun onStart() {
        super.onStart()
        galleryViweModel.getAllElements()
        galleryViweModel.getMainImages()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}