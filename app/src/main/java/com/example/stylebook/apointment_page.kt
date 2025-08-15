package com.example.stylebook

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.TextView
import android.widget.TimePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.Calendar

class apointment_page : AppCompatActivity() {
    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_apointment_page)



        val ecname = findViewById<EditText>(R.id.ecname)
        val enumber = findViewById<EditText>(R.id.enumber)
        val cservice = findViewById<Spinner>(R.id.cservice)
        val cstyle = findViewById<Spinner>(R.id.cstyle)
        val cstaff = findViewById<Spinner>(R.id.cstaff)
        val  date_picker_container = findViewById<LinearLayout>(R.id.date_picker_container)
        val  tvSelectedDate = findViewById<TextView>(R.id.tvSelectedDate)
        val time_picker_container = findViewById<LinearLayout>(R.id.time_picker_container)
        val tvSelectedTime = findViewById<TextView>(R.id.tvSelectedTime)

        val btninsert = findViewById<Button>(R.id.btninsert)
        val footer = findViewById<BottomNavigationView>(R.id.footer)
        val demo = DataBaseDemo(this)





        val service = demo.readser().toMutableList()
        service.add(0, "Select service")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, service)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        cservice.adapter = adapter

        val style = demo.readset().toMutableList()
        style.add(0, "Select style")
        val adapter2 = ArrayAdapter(this, android.R.layout.simple_spinner_item, style)
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        cstyle.adapter = adapter2

        val staff = demo.readwor().toMutableList()
        staff.add(0, "Select staff member")
        val adapter3 = ArrayAdapter(this, android.R.layout.simple_spinner_item, staff)
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        cstaff.adapter = adapter3




        date_picker_container.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePicker = DatePickerDialog(this, { _, selYear, selMonth, selDay ->
                val selectedDate = String.format("%02d-%02d-%04d", selDay, selMonth + 1, selYear)
                tvSelectedDate.text = selectedDate
            }, year, month, day)

            datePicker.show()
        }

        time_picker_container.setOnClickListener {
            val calendar = Calendar.getInstance()
            val hour = calendar.get(Calendar.HOUR_OF_DAY)
            val minute = calendar.get(Calendar.MINUTE)

            val timePicker = TimePickerDialog(this, { _, selectedHour, selectedMinute ->
                var hourFormatted = selectedHour
                val amPm: String

                if (selectedHour >= 12) {
                    amPm = "PM"
                    if (selectedHour > 12) hourFormatted -= 12
                } else {
                    amPm = "AM"
                    if (selectedHour == 0) hourFormatted = 12
                }

                val selectedTime = String.format("%02d:%02d %s", hourFormatted, selectedMinute, amPm)
                tvSelectedTime.text = selectedTime
            }, hour, minute, false)

            timePicker.show()
        }




        ecname.addTextChangedListener(object  : TextWatcher{

            override fun afterTextChanged(p0: Editable?) {}
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                var custName = ecname.text.toString()

                if (custName.isEmpty()){

                    ecname.error = "Please enter customer name"
                }else{

                    ecname.error = null
                }


            }
        })


        enumber.addTextChangedListener(object : TextWatcher{

            override fun afterTextChanged(p0: Editable?) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                val custNumber = enumber.text.toString()
                if (custNumber.isEmpty() || custNumber.length != 10 || ! custNumber.all{it.isDigit()}){

                    enumber.error = "Enter 10 digit mobile number"

                }else{

                    enumber.error = null
                }


            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        })















        btninsert.setOnClickListener {

            var custName = ecname.text.toString()
            val custNumber = enumber.text.toString()
            var custSer = cservice.selectedItem.toString()
            var custStyle = cstyle.selectedItem.toString()
            var staff = cstaff.selectedItem.toString()


            val custDate = tvSelectedDate.text.toString()


            var custTime = tvSelectedTime.text.toString()


            if (custName.isEmpty()){

                return@setOnClickListener
            }

            if (custNumber.isEmpty() || custNumber.length != 10 || ! custNumber.all{it.isDigit()}){

                return@setOnClickListener

            }

            if (custSer == "Select service") {
                Toast.makeText(this, "Please select a service", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }


            if (custStyle == "Select style") {
                Toast.makeText(this, "Please select a style", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (custDate.isEmpty() || custDate == "Select Date") {
                Toast.makeText(this, "Please select a date", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }


            if (custTime.isEmpty() || custTime == "Select Time") {
                Toast.makeText(this, "Please select a time", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if(staff == "Select staff member" || staff.isEmpty()){

                Toast.makeText(this, "Please select a staff member", Toast.LENGTH_SHORT).show()
                return@setOnClickListener

            }



             if(tvSelectedDate.text == "Select Date" || tvSelectedDate.text.isEmpty()){

                        Toast.makeText(this, "Please select a Date", Toast.LENGTH_SHORT).show()
                        return@setOnClickListener

             }

            if(tvSelectedTime.text == "Select Time" || tvSelectedTime.text.isEmpty()){

                      Toast.makeText(this,"Please select a time",Toast.LENGTH_LONG).show()
                      return@setOnClickListener
            }





            val cursor1 = demo.readsercharge(custSer)
            var serchrge = 0.0
            if (cursor1.moveToFirst()) {
                serchrge = cursor1.getDouble(cursor1.getColumnIndexOrThrow(DataBaseDemo.SER_CHAR))
            }
            cursor1.close()

            val cursor2 = demo.readstycharge(custStyle)
            var stcharge = 0.0
            if (cursor2.moveToFirst()) {
                stcharge = cursor2.getDouble(cursor2.getColumnIndexOrThrow(DataBaseDemo.STY_CHAR))
            }
            cursor2.close()

            val totalcharge = serchrge + stcharge
            val charge = totalcharge.toString()

            demo.insertData(custName,  custNumber, custStyle, custSer,staff, charge, custDate, custTime)
            demo.updateWorkerStats(staff)

            ecname.text.clear()
            enumber.text.clear()
            cservice.setSelection(0)
            cstyle.setSelection(0)
            cstaff.setSelection(0)
            tvSelectedDate.text = "Select Date"
            tvSelectedTime.text = "Select Time"


            Toast.makeText(this, "Appointment Successfully", Toast.LENGTH_LONG).show()

        }



        footer.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.icon_home -> {

                    val intent = Intent(this,Homepage::class.java)
                    startActivity(intent)
                    true

                }
                R.id.staff -> {
                    val intent = Intent(this,stafff::class.java)
                    startActivity(intent)
                    true
                }
                R.id.icon_list -> {
                    val intent = Intent(this,Display_page::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }


    }

}



