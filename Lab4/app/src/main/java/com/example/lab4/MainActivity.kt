package com.example.lab4


import android.app.Activity
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.lab4.adapter.GalleryItem
import com.example.lab4.details.GalleryDetailsActivity
import com.example.lab4.fragments.GalleryDetailsFragment

class MainActivity : AppCompatActivity() {

    lateinit var galleryController: GalleryController
    private lateinit var galleryDetailsFragment: GalleryDetailsFragment
    var pointedIndex : Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_main)
        galleryController = GalleryController(this)
        galleryDetailsFragment = GalleryDetailsFragment()

        if (savedInstanceState != null) {
            val images = savedInstanceState.getSerializable("images") as ArrayList<GalleryItem>
            galleryController.resetImageList(images)
            pointedIndex = savedInstanceState.getInt("index", 0)
        } else {
            galleryController.loadImages()
        }
        galleryController.loadData();


        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            onDetailsFragmentRender(pointedIndex)
        }
    }

    fun makeDetailsIntent(itemIndex: Int){
        if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            val intent = Intent(this, GalleryDetailsActivity::class.java)
            intent.putExtra("index", itemIndex)
            intent.putExtra("galleryItem", galleryController.getItemData(itemIndex))
            startActivityForResult(intent, 1)
        } else {
            onDetailsFragmentRender(itemIndex)
        }
    }

    private fun onDetailsFragmentRender(itemIndex: Int) {
        // Create new fragment and transaction
        galleryDetailsFragment = GalleryDetailsFragment.newInstance(itemIndex, galleryController.getItemData(itemIndex))
        val transaction = supportFragmentManager.beginTransaction()
        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack if needed
        transaction.replace(R.id.landscape_frame_layout, galleryDetailsFragment)
        transaction.addToBackStack(null)
        // Commit the transaction
        transaction.commit()
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val index: Int
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    index = data.getIntExtra("index", 0)
                    val galleryItem = data.getSerializableExtra("image") as GalleryItem
                    galleryController.onRatingChange(index, galleryItem.rating)
                }
            }
        }
    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (outState != null) {
            outState.putSerializable("images", galleryController.imagesList)
            outState.putInt("index", pointedIndex)
        }
    }
    override fun onStop() {
        super.onStop()
        println("On stop")
        galleryController.saveData()
    }

}