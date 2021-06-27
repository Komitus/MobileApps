package com.example.lab4

import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lab4.MainActivity
import com.example.lab4.adapter.GalleryItem
import java.io.*

class GalleryController(private val mainActivity: MainActivity) {

    private val descriptions: Array<String> = mainActivity.resources.getStringArray(R.array.descriptions)
    private val saveFile: File = File(mainActivity.filesDir, "gallery.data")
    private lateinit var thumbnailsDir : File
    private var recycleView : RecyclerView = mainActivity.findViewById<RecyclerView>(R.id.recycleView)
    var  imagesList: ArrayList<GalleryItem> = arrayListOf()

    init{
        recycleView.adapter = RecycleAdapter(imagesList, mainActivity)
        recycleView.layoutManager = LinearLayoutManager(mainActivity)
    }

    fun getItemData(itemId : Int) : GalleryItem{
        return imagesList[itemId];
    }

    fun onRatingChange(index: Int, rating: Float) {
        val imageId = imagesList[index].imgId
        imagesList[index].rating = rating
        imagesList.sortByDescending { it.rating }
        mainActivity.pointedIndex = imagesList.indexOfFirst { it.imgId == imageId}
        recycleView.adapter!!.notifyDataSetChanged()
    }
    fun resetImageList(passedImagesList : ArrayList<GalleryItem> ) {
        imagesList.clear()
        for (item in passedImagesList) {
            val newItem = GalleryItem(item.imgId, item.rating, item.description)
            imagesList.add(newItem)
        }
    }

    fun saveData() {
        ObjectOutputStream(FileOutputStream(saveFile)).use {
            it.writeObject(imagesList)
        }
    }

    fun loadData() {
        if (!saveFile.exists()) {
            return
        }

        ObjectInputStream(FileInputStream(saveFile)).use {

            when (val loadedData = it.readObject()) {
                is ArrayList<*> -> {
                    addLoadedData(loadedData)
                }
                else -> println("Deserialization failed")
            }
        }
    }
    private fun addLoadedData(data: ArrayList<*>) {
        imagesList.clear()
        for (item in data) {
            val image = item as GalleryItem
            val index = containsImageWithID(image.imgId)

            if (index != -1) {
                imagesList[index].rating = image.rating
                imagesList[index].description = image.description
            } else {
                val newImg = GalleryItem(image.imgId, image.rating, image.description)
                imagesList.add(newImg)
            }
        }
    }
    private fun containsImageWithID(id: Int): Int {
        imagesList.forEachIndexed { index, element ->
            if (element.imgId == id) {
                return index
            }
        }
        return -1
    }
    fun loadImages(){
        //thumbnailsDir = File("/mnt/sdcard/Lab4Images/Thumbnails")
        var numOfImages = 13//thumbnailsDir.listFiles().size
        Log.i("moje", numOfImages.toString())
        var iterator = 1
        while(iterator <= numOfImages){
            val image = GalleryItem(iterator, 0f, descriptions[iterator-1])
            imagesList.add(image)
            iterator++
        }
        recycleView.adapter!!.notifyDataSetChanged()
    }
}