package com.example.ex1

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Parcelable
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.internal.ParcelableSparseArray
import java.time.LocalDateTime


class MainActivity : AppCompatActivity() {
    lateinit var tasksList : ArrayList<SingleTask>
    private lateinit var dateButton : Button
    private lateinit var priorityButton : Button
    private lateinit var iconButton : Button
    private lateinit var sortOrderButton : ImageView
    private lateinit var rcV : RecyclerView
    private lateinit var trashIcon : ImageButton
    private lateinit var editIcon : ImageButton
    private var sortBy : String = "DATE"
    private var sortOrder : Int = 0 //desc
    private var itemToEdit : Int? = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        trashIcon = findViewById<ImageButton>(R.id.trashIcon)
        editIcon = findViewById<ImageButton>(R.id.editIcon)
        dateButton = findViewById<Button>(R.id.dateSortButton)
        priorityButton = findViewById<Button>(R.id.prioritySortButton)
        iconButton = findViewById<Button>(R.id.iconSortButton)
        tasksList = arrayListOf(

                SingleTask("MobileApps", "ToDoList - made up points", PRIORITY.NORMAL, R.drawable.outline_work_black_24,
                        LocalDateTime.of(2021, 4, 16, 14, 5)),
                SingleTask("AIDS", "Algorithms - median and other stuff", PRIORITY.ASAP, R.drawable.outline_home_black_48,
                        LocalDateTime.of(2021, 4, 16, 13, 50)),
                SingleTask("Test For Long Text", "It is a long established fact that a reader will be " +
                        "distracted by the readable content of a page when looking at its layout. The point of using Lorem Ipsum " +
                        "is that it has a more-or-less normal distribution of letters, as opposed to using 'Content here, content here', " +
                        "making it look like readable English. Many desktop publishing packages and web page editors now use Lorem Ipsum as " +
                        "their default model text, and a search for 'lorem ipsum' will uncover many web sites still in their infancy. Various " +
                        "versions have evolved over the years, sometimes by accident, sometimes on purpose (injected humour and the like).",
                        PRIORITY.LAZY, R.drawable.pie, LocalDateTime.of(2021, 2, 13, 20, 11)),
                SingleTask("Test4", "Simple desc test", PRIORITY.NORMAL, R.drawable.outline_work_black_24,
                        LocalDateTime.of(2021, 5, 20, 13, 1)),

                )
        trashIcon.isVisible = false
        editIcon.isVisible = false
        rcV = this.findViewById<RecyclerView>(R.id.recyclerView)
        rcV.adapter = RecycleAdapter(tasksList, trashIcon, editIcon)
        rcV.layoutManager = LinearLayoutManager(this)
    }


    fun addTask(view: View){
        val myIntent = Intent(this, AddTaskActivity::class.java)
        myIntent.putExtra("type", "add")
        startActivityForResult(myIntent, 1)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null) {
            if(requestCode == 2 ){
                tasksList[itemToEdit!!] = data?.getParcelableExtra<SingleTask>("task") as SingleTask
                rcV.adapter?.notifyItemChanged(itemToEdit!!)
                Toast.makeText(this, "Succesfully edited", Toast.LENGTH_SHORT).show()
            }
            if (requestCode == 1 ) {
                val newTask = data?.getParcelableExtra<SingleTask>("task") as SingleTask
                tasksList.add(newTask)
                rcV.adapter!!.notifyItemChanged(tasksList.size)
            }
        }
    }
    fun sortBy(view: View){

        dateButton.setBackgroundColor(Color.rgb(113, 113, 113))
        priorityButton.setBackgroundColor(Color.rgb(113, 113, 113))
        iconButton.setBackgroundColor(Color.rgb(113, 113, 113))
        view.setBackgroundColor(Color.rgb(0, 153, 0))
        sortOrder = (sortOrder+1)%2

        sortBy = (view as Button).text.toString()

        when(sortBy) {
            "Date" -> {
                if (sortOrder == 0)
                    tasksList.sortBy { it.dateTime }
                else
                    tasksList.sortByDescending { it.dateTime }
            }
            "Priority" -> {
                if (sortOrder == 0)
                    tasksList.sortBy { it.priority }
                else
                    tasksList.sortByDescending { it.priority }
            }
            "Category" -> {
                if (sortOrder == 0)
                    tasksList.sortBy { it.taskImage }
                else
                    tasksList.sortByDescending { it.taskImage }
            }
        }
        rcV.adapter!!.notifyDataSetChanged()

    }

    fun removeItems(view: View){
        (rcV.adapter as RecycleAdapter).removeSelected()
    }
    fun editItem(view: View){
        itemToEdit = (rcV.adapter as RecycleAdapter).editSelected()
        if(itemToEdit != null){
            val myIntent = Intent(this, AddTaskActivity::class.java)
            myIntent.putExtra("type", "edit")
            myIntent.putExtra("data", tasksList[itemToEdit!!])
            startActivityForResult(myIntent, 2)
        }
    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList("tasksList", tasksList)
    }
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        tasksList = savedInstanceState.getParcelableArrayList<SingleTask>("tasksList") as ArrayList<SingleTask>
        rcV.adapter = RecycleAdapter(tasksList, trashIcon, editIcon)
    }
}

