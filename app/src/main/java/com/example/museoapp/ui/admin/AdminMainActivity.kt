package com.example.museoapp.ui.admin

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ViewModelProvider
import com.example.museoapp.R
import com.example.museoapp.ViewModel.GalleryViewModel
import com.example.museoapp.databinding.ActivityAdminMainBinding
import com.example.museoapp.model.FireBase.Auth
import com.example.museoapp.model.adapter.ItemAdapter

class AdminMainActivity : AppCompatActivity(), LifecycleObserver{
    private lateinit var binding: ActivityAdminMainBinding
    private val galleryViweModel : GalleryViewModel by viewModels()
    private val adminMainViewModel : AdminMainViewModel by viewModels()
    private var authObj = Auth()
    private var auth = authObj.getAuth()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_main)

        binding = ActivityAdminMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adminMainViewModel = ViewModelProvider(this).get(AdminMainViewModel::class.java)

        galleryViweModel.getAllElements()
        galleryViweModel.galleriesObjects.observe(this){ it ->
            Log.i(TAG, "Desntro de cargar los elementos")
            if (it != null){
                //elementsGallery = it
                val recyclerView = binding.recyclerViewAdmin
                val adapter = ItemAdapter(this, it)
                recyclerView.adapter = adapter
                val activity = this
                adapter.setOnItemClickListener(object : ItemAdapter.onItemClickListener{
                    override fun onItemClick(position: Int) {
                        adminMainViewModel.itemClicked(position, it, activity)
                    }
                })
                recyclerView.setHasFixedSize(true)
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        //return super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.menu_app_bar_admin, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //return super.onOptionsItemSelected(item)

        return when(item.itemId) {
            R.id.add_item -> {
                adminMainViewModel.startFormAdd(this)
                true
            }

            R.id.log_out_admin -> {
                adminMainViewModel.log_out()
                updateLogout()

                true
            }

            else -> { super.onOptionsItemSelected(item) }
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START) fun getAll() {
        galleryViweModel.getAllElements()
    }

    private fun updateLogout(){
        if (auth?.currentUser == null){
            finish()
        }
    }
}