package com.example.ex1

import android.graphics.Color
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.ex1.databinding.SingleItemOnListBinding
import java.time.LocalDate
import java.time.LocalTime

class RecycleAdapter(var data: ArrayList<SingleTask>, var trashIcon: ImageButton, var editIcon: ImageButton) : RecyclerView.Adapter<RecycleAdapter.ViewHolder>(){

    lateinit var binding: SingleItemOnListBinding
    var removeList : ArrayList<Int> =  arrayListOf()
    private val myInterface : DateToString = DateToString()

    private val SAVED_SUPER_STATE = "super-state"
    private val SAVED_LAYOUT_MANAGER = "layout-manager-state"
    private lateinit var mLayoutManagerSavedState: Parcelable


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding  = SingleItemOnListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return data.size
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setData(data[position])
        holder.itemView.setOnTouchListener(object : OnSwipeTouchListener(binding.root.context) {

            override fun onSwipeRight() {

                super.onSwipeRight()
                val itemPosition: Int = holder.layoutPosition
                data.removeAt(itemPosition)

                notifyItemRemoved(itemPosition)
            }

            override fun onLongClick() {
                trashIcon.isVisible = true
                editIcon.isVisible = true
                val itemPosition: Int = holder.layoutPosition
                if (removeList.contains(itemPosition)) {
                    if (removeList.size == 1)
                        trashIcon.isVisible = false
                    editIcon.isVisible = false
                    removeList.remove(itemPosition)
                } else
                    removeList.add(itemPosition)

                notifyItemChanged(itemPosition)
            }
        })
        if(removeList.contains(position)) holder.itemView.setBackgroundColor(Color.DKGRAY);
        else holder.itemView.setBackgroundColor(Color.WHITE)

    }



    inner class ViewHolder(private val localBinding: SingleItemOnListBinding) : RecyclerView.ViewHolder(localBinding.root){

        fun setData(singleTask: SingleTask) {
            localBinding.taskTitle.text = singleTask.title
            localBinding.taskDesc.text = singleTask.description
            //applay here doesnt work, idk why
            localBinding.date.text = myInterface.makeDateToString(
                LocalDate.of(singleTask.dateTime.year,
                    singleTask.dateTime.month,
                    singleTask.dateTime.dayOfMonth)
            )
            localBinding.time.text = myInterface.makeTimeToString(
                LocalTime.of(singleTask.dateTime.hour, singleTask.dateTime.minute)
            )
            when(singleTask.priority) {
                PRIORITY.LAZY -> localBinding.taskPriority.text = PRIORITY.LAZY.toString()
                PRIORITY.NORMAL -> localBinding.taskPriority.text = PRIORITY.NORMAL.toString()
                PRIORITY.ASAP -> localBinding.taskPriority.text = PRIORITY.ASAP.toString()
            }
            singleTask.taskImage?.let { localBinding.taskImg.setBackgroundResource(it) }
        }
    }

    fun removeSelected(){

        removeList.sortDescending();
        for(i in removeList){
            data.removeAt(i)
        }
        notifyDataSetChanged()
        removeList.removeAll(removeList)
        trashIcon.isVisible = false
    }
    fun editSelected(): Int? {

        if(removeList.isNotEmpty()){
            return removeList[0]
        }
        else return null

    }

}
