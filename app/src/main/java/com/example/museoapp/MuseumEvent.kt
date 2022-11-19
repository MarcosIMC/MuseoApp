package com.example.museoapp

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class MuseumEvent : Fragment() {

    companion object {
        fun newInstance() = MuseumEvent()
    }

    private lateinit var viewModel: MuseumEventViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_museum_event, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MuseumEventViewModel::class.java)
        // TODO: Use the ViewModel
    }

}