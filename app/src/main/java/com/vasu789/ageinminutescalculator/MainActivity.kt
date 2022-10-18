package com.vasu789.ageinminutescalculator

import android.app.DatePickerDialog
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.DatePicker
import android.widget.TextView
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    private var tvSelectedDate : TextView? = null
    private var tvAgeInMinutes : TextView? = null
    private var tvSelectedDateText : TextView? = null
    private var tvAgeInMinutesText : TextView? = null
    private var tvInMinutes : TextView? = null
    private var triFlag : Int = 1
    private var typeText : String? = null
    private var timeInMinutes : Long? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val typeTimeButton : Button = findViewById(R.id.typeTimeButton)
        tvInMinutes = findViewById(R.id.tvInMinutes)
        tvAgeInMinutes = findViewById(R.id.tvAgeInMinutes)
        tvAgeInMinutesText = findViewById(R.id.tvAgeInMinutesText)
        typeTimeButton.setOnClickListener{
            typeText = checkTriFlagText()
            typeTimeButton.text = typeText
            tvInMinutes?.text = "In $typeText"
            if(timeInMinutes != null)
            {
                tvAgeInMinutes?.text = (timeInMinutes!! / getTypeTimeDivider()).toString()
                tvAgeInMinutesText?.text = "Age In $typeText"
            }
            //Toast.makeText(this, "Working", Toast.LENGTH_SHORT).show()
        }

        val btnDatePicker : Button = findViewById(R.id.btnDatePicker)
        tvSelectedDate = findViewById(R.id.tvSelectedDate)
        tvSelectedDateText = findViewById(R.id.tvSelectedDateText)
        btnDatePicker.setOnClickListener{
            clickDatePicker()
        }
    }

    private fun checkTriFlagText() : String
    {
        if(triFlag == 3)
        {
            triFlag = 1
        }
        else
        {
            triFlag++
        }
        return when(triFlag) {
            1 -> "Minutes"
            2 -> "Hours"
            3 -> "Days"
            else -> "Unknown Error"
        }
    }

    private fun getTypeTimeDivider() : Int
    {
        return when(triFlag) {
            1 -> 1
            2 -> 60
            3 -> 60*24
            else -> 0
        }
    }

    private fun clickDatePicker()
    {
        val myCalendar = Calendar.getInstance()
        val year = myCalendar.get(Calendar.YEAR)
        val month = myCalendar.get(Calendar.MONTH)
        val day = myCalendar.get(Calendar.DAY_OF_MONTH)
        val dpd = DatePickerDialog(this,
            DatePickerDialog.OnDateSetListener{view, selectedYear, selectedMonth, selectedDayOfMonth ->
                //Toast.makeText(this, "Date Was $selectedDayOfMonth/${selectedMonth+1}/$selectedYear", Toast.LENGTH_SHORT).show()
                val selectedDate = "$selectedDayOfMonth/${selectedMonth+1}/$selectedYear"
                tvSelectedDate?.text = selectedDate
                tvSelectedDateText?.text = "Selected Date"
                val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
                val theDate = sdf.parse(selectedDate)
                theDate?.let{
                    val selectedDateInMinutes = theDate.time / 60000
                    val currentDate = sdf.parse(sdf.format(System.currentTimeMillis()))
                    currentDate?.let {
                        val currentDateInMinutes = currentDate.time / 60000
                        val differenceInMinutes = currentDateInMinutes - selectedDateInMinutes
                        timeInMinutes = differenceInMinutes
                        tvAgeInMinutes?.text = (timeInMinutes!! / getTypeTimeDivider()).toString()
                        tvAgeInMinutesText?.text = "Age In $typeText"
                    }
                }

            },
            year,
            month,
            day
        )
        dpd.datePicker.maxDate = System.currentTimeMillis() - 86400000
        dpd.show()


    }
}