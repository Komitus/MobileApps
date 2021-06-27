package com.example.lab4.fragments

import android.R.attr
import android.content.res.Configuration
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Environment
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import com.example.lab4.MainActivity
import com.example.lab4.R
import com.example.lab4.adapter.GalleryItem
import com.example.lab4.databinding.FragmentGalleryDetailsBinding
import java.io.FileNotFoundException


class GalleryDetailsFragment : Fragment() {
    var index: Int = 0
    lateinit var galleryItem : GalleryItem
    private lateinit var binding : FragmentGalleryDetailsBinding

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        arguments?.let{
            galleryItem = arguments!!.get("galleryItem") as GalleryItem
            index = arguments!!.get("index") as Int
            binding.fragmentText.text =  galleryItem.description
            binding.fragmentImage.setImageDrawable(readFromExternal(galleryItem.imgId))
            binding.fragmentRating.rating = galleryItem.rating
        }

        binding.fragmentRating.setOnRatingBarChangeListener { _, rating, _ ->
            galleryItem.rating = binding.fragmentRating.rating
            if (activity?.resources?.configuration?.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                (activity as MainActivity).galleryController.onRatingChange(index, rating)
                index = (activity as MainActivity).pointedIndex
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view =  inflater.inflate(R.layout.fragment_gallery_details, container, false)
        binding = FragmentGalleryDetailsBinding.bind(view)
        binding.fragmentText.movementMethod = ScrollingMovementMethod()
        return  view
    }

    companion object {
        @JvmStatic
        fun newInstance(index: Int, galleryItem: GalleryItem) =
            GalleryDetailsFragment().apply {
                arguments = Bundle().apply {
                    putInt("index", index)
                    putSerializable("galleryItem", galleryItem)
                }
            }
    }
    private fun readFromExternal(imgId: Int) : Drawable?{
        var d : Drawable?
        var options = BitmapFactory.Options()
        var bitmap : Bitmap
        val imageInSD = "/sdcard/Lab4Images/Original/img_$imgId.jpg"

        try {
            options.inPreferredConfig = Bitmap.Config.ARGB_8888
             bitmap =  BitmapFactory.decodeFile(imageInSD, options)
             d = BitmapDrawable(bitmap)
        } catch (e : NullPointerException) {
            d =  AppCompatResources.getDrawable(context!!, R.drawable.ic_launcher_background);
        }
        catch ( e : FileNotFoundException){
            d =  AppCompatResources.getDrawable(context!!, R.drawable.ic_launcher_background);
        }
        return d
    }
}