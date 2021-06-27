package com.example.lab4

import android.content.res.Configuration
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.isVisible

import androidx.recyclerview.widget.RecyclerView
import com.example.lab4.adapter.GalleryItem
import java.io.FileNotFoundException


class RecycleAdapter(var dataSet: ArrayList<GalleryItem>, private val mainActivity: MainActivity) : RecyclerView.Adapter<RecycleAdapter.ViewHolder>(){

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView : ImageView;
        val ratingBar : RatingBar
        init {
            imageView = view.findViewById<ImageView>(R.id.imageViewInRecycler)
            ratingBar = view.findViewById<RatingBar>(R.id.ratingBarInRecycler)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.gallery_item, viewGroup, false)

        return ViewHolder(view)
    }


    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val item = dataSet[position]
        if(mainActivity.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT){
            viewHolder.ratingBar.rating = item.rating
        } else {
            viewHolder.ratingBar.isVisible = false
        }
        viewHolder.imageView.setImageDrawable(readFromExternal(item.imgId))
        viewHolder.itemView.setOnClickListener{
            mainActivity.makeDetailsIntent(position)
        }
    }

    override fun getItemCount() = dataSet.size

    private fun readFromExternal(imgId: Int) : Drawable?{
        var d : Drawable?
        var options = BitmapFactory.Options()
        var bitmap : Bitmap
        val imageInSD = "/sdcard/Lab4Images/Thumbnails/img_$imgId.jpg"

        try {
            options.inPreferredConfig = Bitmap.Config.ARGB_8888
            bitmap =  BitmapFactory.decodeFile(imageInSD, options)
            d = BitmapDrawable(bitmap)
        } catch (e : NullPointerException) {
            d =  AppCompatResources.getDrawable(mainActivity.applicationContext!!, R.drawable.ic_launcher_background);
        }
        catch ( e : FileNotFoundException){
            d =  AppCompatResources.getDrawable(mainActivity.applicationContext!!, R.drawable.ic_launcher_background);
        }
        return d
    }
}










