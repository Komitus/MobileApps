package com.example.ex1


import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable
import java.lang.reflect.Constructor
import java.time.LocalDateTime


enum class PRIORITY(){
    LAZY, NORMAL, ASAP
}

data class SingleTask  (
        val title: String,
        val description: String,
        val priority: PRIORITY,
        val taskImage: Int,
        val dateTime: LocalDateTime,
) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString().toString(),
            parcel.readString().toString(),
            parcel.readValue(PRIORITY::class.java.classLoader) as PRIORITY,
            parcel.readValue(Int::class.java.classLoader) as Int,
            parcel.readValue(LocalDateTime::class.java.classLoader) as LocalDateTime)

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(description)
        parcel.writeValue(priority)
        parcel.writeValue(taskImage)
        parcel.writeValue(dateTime)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SingleTask> {
        override fun createFromParcel(parcel: Parcel): SingleTask {
            return SingleTask(parcel)
        }

        override fun newArray(size: Int): Array<SingleTask?> {
            return arrayOfNulls(size)
        }
    }
}