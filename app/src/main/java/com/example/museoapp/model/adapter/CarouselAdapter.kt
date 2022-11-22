package com.example.museoapp.model.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.museoapp.R

class CarouselAdapter (private  var list: ArrayList<String>): RecyclerView.Adapter<CarouselAdapter.ViewHolder>() {
    class ViewHolder (itemView: View): RecyclerView.ViewHolder(itemView) {
        val image : ImageView = itemView.findViewById(R.id.image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.carousel_item_view, parent, false)
        return ViewHolder(inflater)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(holder.image).load(list.get(position)).into(holder.image)
    }

    override fun getItemCount(): Int {
        return list.size
    }
}