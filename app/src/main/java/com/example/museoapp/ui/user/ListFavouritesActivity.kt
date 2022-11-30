package com.example.museoapp.ui.user

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import com.example.museoapp.R
import com.example.museoapp.ViewModel.GalleryViewModel
import com.example.museoapp.ViewModel.ListFavouritesViewModel
import com.example.museoapp.ViewModel.UserDataViewModel
import com.example.museoapp.databinding.ActivityListFavouritesBinding
import com.example.museoapp.model.FireBase.UserFireBase
import com.example.museoapp.model.GalleryModel
import com.example.museoapp.model.adapter.ItemAdapter

class ListFavouritesActivity : AppCompatActivity() {

    private var _binding: ActivityListFavouritesBinding? = null
    private val binding get() = _binding!!
    private val listFavouritesVM : ListFavouritesViewModel by viewModels()
    private val galleryViweModel : GalleryViewModel by viewModels()
    private val userFireBase = UserFireBase()
    private lateinit var elementsGallery : MutableList<GalleryModel>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_favourites)

        galleryViweModel.getSelectedElements()

        galleryViweModel.galleriesObjects.observe(this){ it ->
            if (it != null){
                elementsGallery = it
                val recyclerView = binding.recyclerViewFavourites
                val adapter = ItemAdapter(this, it)
                recyclerView.adapter = adapter
                adapter.setOnItemClickListener(object : ItemAdapter.onItemClickListener{
                    override fun onItemClick(position: Int) {
                        listFavouritesVM.itemClicked(position, it, applicationContext)
                    }
                })
                recyclerView.setHasFixedSize(true)
            }
        }
    }
}