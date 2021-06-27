package com.example.ex1

import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.View
import android.widget.*

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

import java.util.*

class AddTaskActivity : AppCompatActivity() {

    private lateinit var datePickerDialog : DatePickerDialog
    private lateinit var dateButton : Button
    private lateinit var dateForReturn : LocalDate

    private lateinit var timePickerDialog : TimePickerDialog
    private lateinit var timeButton : Button
    private lateinit var timeForReturn : LocalTime

    private lateinit var descEditText : EditText
    private lateinit var titleText : EditText
    private lateinit var prioritySpinner: Spinner
    private lateinit var categorySpinner: Spinner
    private lateinit var receivedTask : SingleTask
    private var intentMode : String? = "add"
    private val myInterface : DateToString = DateToString()

    private var priority : PRIORITY = PRIORITY.LAZY
    private var choosen_img : Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)
        supportActionBar?.hide()

        initDatePicker()
        initTimePicker()
        dateButton = findViewById<Button>(R.id.dateButton)
        timeButton = findViewById<Button>(R.id.timeButton)
        titleText = findViewById<EditText>(R.id.inputTitle)

        descEditText = findViewById<EditText>(R.id.inputDesc)
        descEditText.setScroller(Scroller(descEditText.context));
        descEditText.maxLines = 4;
        descEditText.isVerticalScrollBarEnabled = true;
        descEditText.movementMethod = ScrollingMovementMethod();

        dateForReturn = LocalDate.now()
        timeForReturn = LocalTime.now()

        intentMode = intent.getStringExtra("type")

        if(intentMode.equals("add" )){
            dateButton.text = myInterface.makeDateToString(LocalDate.now())
            timeButton.text =  myInterface.makeTimeToString(LocalTime.now())
        }
        else{
            receivedTask = intent.getParcelableExtra<SingleTask>("data") as SingleTask
            titleText.setText(receivedTask.title)
            descEditText.setText(receivedTask.description)

            dateButton.text = myInterface.makeDateToString(
                    LocalDate.of(receivedTask.dateTime.year,
                            receivedTask.dateTime.month,
                            receivedTask.dateTime.dayOfMonth)
            )
            timeButton.text = myInterface.makeTimeToString(
                    LocalTime.of(receivedTask.dateTime.hour, receivedTask.dateTime.minute)
            )
        }


        categorySpinner = findViewById<Spinner>(R.id.categorySpinner)
        categorySpinner.onItemSelectedListener = object  : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when(position){
                        0 -> choosen_img = R.drawable.outline_work_black_24
                        1 -> choosen_img = R.drawable.outline_accessibility_new_black_48
                        2 -> choosen_img = R.drawable.outline_home_black_48
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        prioritySpinner = findViewById<Spinner>(R.id.prioritySpinner)
        prioritySpinner.onItemSelectedListener = object  : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when(position){
                        0 -> priority = PRIORITY.LAZY
                        1 -> priority = PRIORITY.NORMAL
                        2 -> priority = PRIORITY.ASAP
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }
    private fun initDatePicker(){
        var dateSetListener : DatePickerDialog.OnDateSetListener = DatePickerDialog.OnDateSetListener {
            _, year, month, dayOfMonth ->
            val tmp =  LocalDate.of(year, month+1, dayOfMonth)
            dateForReturn = tmp
            dateButton.text = myInterface.makeDateToString(tmp)
        }

        val cal : Calendar = Calendar.getInstance()
        val year : Int = cal.get(Calendar.YEAR)
        val month: Int = cal.get(Calendar.MONTH)
        val day : Int = cal.get(Calendar.DAY_OF_MONTH)

        val style : Int = R.style.Theme_MaterialComponents_Light_Dialog_Alert

        datePickerDialog = DatePickerDialog(this, style,dateSetListener, year, month, day)
        datePickerDialog.datePicker.minDate = System.currentTimeMillis()
    }
    private fun initTimePicker(){
        var timeSetListener : TimePickerDialog.OnTimeSetListener = TimePickerDialog.OnTimeSetListener{
            _, hour, minute ->
            val tmp = LocalTime.of(hour, minute)
            timeForReturn = tmp
            timeButton.text = myInterface.makeTimeToString(tmp)

        }
        val cal : Calendar = Calendar.getInstance()
        val hour : Int = cal.get(Calendar.HOUR_OF_DAY)
        val minute : Int = cal.get(Calendar.MINUTE)

        val style : Int = R.style.Theme_MaterialComponents_Light_Dialog_Alert

        timePickerDialog = TimePickerDialog(this, timeSetListener, hour, minute, true)
    }

    fun openDatePicker(view: View) {
        datePickerDialog.show()
    }
    fun openTimePicker(view: View) {
        timePickerDialog.show()
    }



    fun saveAction(view: View){

        if(titleText.text.isNotEmpty() && descEditText.text.isNotEmpty())
        {
            val returnIntent = Intent(this, MainActivity::class.java)
            val dateTimeForRet = LocalDateTime.of(
                    dateForReturn.year, dateForReturn.month, dateForReturn.dayOfMonth,
                    timeForReturn.hour, timeForReturn.minute
            )
            val task = SingleTask(
                    titleText.text.toString(), descEditText.text.toString(),
                    priority, choosen_img, dateTimeForRet
            )
            returnIntent.putExtra("task", task)
            setResult(Activity.RESULT_OK, returnIntent)
            finish()
        }
        else
            Toast.makeText(this, "Fill every input", Toast.LENGTH_SHORT).show()
    }



}

