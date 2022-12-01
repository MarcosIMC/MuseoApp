package com.example.museoapp.model.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.museoapp.R
import com.example.museoapp.model.GalleryModel
import com.example.museoapp.ui.home.HomeFragment

class ItemAdapter(
    private val context: Context,
    private val dataset: MutableList<GalleryModel>
) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    private  lateinit var mListener: onItemClickListener

    interface onItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener){
        mListener = listener
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder.
    // Each data item is just an Affirmation object.
    class ItemViewHolder(private val view: View, listener: onItemClickListener) : RecyclerView.ViewHolder(view) {
        val item_name: TextView = view.findViewById(R.id.item_title)
        val item_sort_description: TextView = view.findViewById(R.id.item_sort_description)
        val item_image: ImageView = view.findViewById(R.id.item_image)

        init {
            view.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }

    /**
     * Create new views (invoked by the layout manager)
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        // create a new view
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)

        return ItemViewHolder(adapterLayout, mListener)
    }

    /**
     * Replace the contents of a view (invoked by the layout manager)
     */
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = dataset[position]
        holder.item_name.text = item.name
        holder.item_sort_description.text = item.sort_description
        Glide.with(context).load(item.image).centerCrop().placeholder(R.drawable.ic_baseline_broken_image_24).error(
            com.google.android.material.R.drawable.mtrl_ic_error).timeout(250).into(holder.item_image)
    }

    /**
     * Return the size of your dataset (invoked by the layout manager)
     */
    override fun getItemCount() = dataset.size
}