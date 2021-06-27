package com.example.lab4.details

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatDialogFragment
import com.example.lab4.R
import com.example.lab4.adapter.GalleryItem
import com.example.lab4.fragments.GalleryDetailsFragment

class GalleryDetailsActivity : AppCompatActivity() {
    private lateinit var galleryDetailsFragment: GalleryDetailsFragment
    private lateinit var galleryItem: GalleryItem
    private var itemIndex : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery_details)

        galleryItem = intent.getSerializableExtra("galleryItem") as GalleryItem
        itemIndex = intent.getIntExtra("index", 0)
        galleryDetailsFragment = GalleryDetailsFragment.newInstance(itemIndex, galleryItem)
        supportFragmentManager.beginTransaction().add(R.id.frame_layout, galleryDetailsFragment).commit()
    }
    override fun onBackPressed() {
        val intent = Intent()
        intent.putExtra("image", galleryDetailsFragment.galleryItem)
        intent.putExtra("index", itemIndex)
        setResult(Activity.RESULT_OK, intent)
        finish()
        super.onBackPressed()
    }
}