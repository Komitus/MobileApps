package com.example.lab4.adapter


import java.io.Serializable

data class GalleryItem  (
    val imgId: Int,
    var rating : Float,
    var description: String,
) : Serializable