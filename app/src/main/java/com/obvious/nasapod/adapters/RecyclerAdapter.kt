package com.obvious.nasapod.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.obvious.nasapod.NasaPhoto
import com.obvious.nasapod.R
import com.obvious.nasapod.extensions.inflate
import com.obvious.nasapod.holders.PhotoHolder

class RecyclerAdapter(private val photos: ArrayList<NasaPhoto>) : RecyclerView.Adapter<PhotoHolder>()  {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoHolder {
        val inflatedView = parent.inflate(R.layout.image_list_item, false)
        return PhotoHolder(inflatedView)
    }

    override fun getItemCount(): Int = photos.size

    override fun onBindViewHolder(holder: PhotoHolder, position: Int) {
        val itemPhoto = photos[position]
        holder.bindPhoto(itemPhoto)
    }



}