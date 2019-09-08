package com.obvious.nasapod.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity;
import com.obvious.nasapod.R
import com.obvious.nasapod.models.NasaPhoto
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_photo.*
import kotlinx.android.synthetic.main.content_photo.*


class PhotoActivity : AppCompatActivity() {

    private var selectedPhoto: NasaPhoto? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_photo)
        setToolbar()

        selectedPhoto = intent.getSerializableExtra(Image_key) as NasaPhoto

        bindData()
    }

    companion object {
        private val Image_key = "IMAGE"
    }

    private fun setToolbar(){
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { finish() }
    }

    private fun bindData(){
        Picasso.with(this).load(selectedPhoto?.url).into(photoImageView)
        photoTitle.text = selectedPhoto?.title
        photoDate.text = selectedPhoto?.date
        photoDescription.text = selectedPhoto?.explanation
    }
}
