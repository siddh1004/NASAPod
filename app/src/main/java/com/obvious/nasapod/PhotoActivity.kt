package com.obvious.nasapod

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity;
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.content_photo.*

class PhotoActivity : AppCompatActivity() {

    private var selectedPhoto: NasaPhoto? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_photo)

        selectedPhoto = intent.getSerializableExtra(Image_key) as NasaPhoto
        Picasso.with(this).load(selectedPhoto?.url).into(photoImageView)

        photoDescription.text = selectedPhoto?.explanation
    }

    companion object {
        private val Image_key = "IMAGE"
    }

}
