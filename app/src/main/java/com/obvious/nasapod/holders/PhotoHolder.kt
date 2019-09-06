package com.obvious.nasapod.holders

import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.obvious.nasapod.PhotoActivity
import com.obvious.nasapod.models.Photo
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.image_list_item.view.*

class PhotoHolder(private val view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
    private var photo: Photo? = null

    init {
        view.setOnClickListener(this)
    }

    fun bindPhoto(photo: Photo) {
        this.photo = photo
        Picasso.with(view.context).load(photo.url).into(view.itemImage)
        view.itemDate.text = photo.humanDate
        view.itemDescription.text = photo.explanation
    }

    override fun onClick(v: View) {
        val context = itemView.context
        val photoIntent = Intent(context, PhotoActivity::class.java)
        photoIntent.putExtra(Image_key, photo)
        context.startActivity(photoIntent)
    }

    companion object {
        private val Image_key = "IMAGE"
    }
}