package com.example.stylebook

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
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
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.Calendar

class Update_appointment : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContentView(R.layout.activity_update_appointment)


        val enumber = findViewById<EditText>(R.id.enumber)
        val ecname = findViewById<EditText>(R.id.ecname)
        val cservice = findViewById<Spinner>(R.id.cservice)
        val cstyle = findViewById<Spinner>(R.id.cstyle)
        val cstaff = findViewById<Spinner>(R.id.cstaff)
        val  date_picker_container = findViewById<LinearLayout>(R.id.date_picker_container)
        val  tvSelectedDate = findViewById<TextView>(R.id.tvSelectedDate)
        val time_picker_container = findViewById<LinearLayout>(R.id.time_picker_container)
        val tvSelectedTime = findViewById<TextView>(R.id.tvSelectedTime)
        val btnupdate = findViewById<Button>(R.id.btnupdate)
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


        val intent = intent

        ecname.setText(intent.getStringExtra("name"))
        enumber.setText(intent.getStringExtra("phone"))


        val serviceValue = intent.getStringExtra("service")
        val styleValue = intent.getStringExtra("style")
        val staffValue = intent.getStringExtra("worker")

        setSpinnerSelection(cservice, serviceValue)
        setSpinnerSelection(cstyle, styleValue)
        setSpinnerSelection(cstaff, staffValue)


        tvSelectedDate.text = intent.getStringExtra("date")
        tvSelectedTime.text = intent.getStringExtra("time")




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





        btnupdate.setOnClickListener{

            var custName =  ecname.text.toString()
            val custNumer = enumber.text.toString()
            var custSer =  cservice.selectedItem.toString()
            var custStyle = cstyle.selectedItem.toString()
            var staff = cstaff.selectedItem.toString()


            val custDate = tvSelectedDate.text.toString()


            var custTime = tvSelectedTime.text.toString()

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




                val id = intent.getStringExtra("id")

                demo.updatedata(id,custName, custNumer,custStyle,custSer,staff,charge,custDate,custTime)




            Toast.makeText(this,"Appointment Update Successfully", Toast.LENGTH_LONG).show()

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

    private fun setSpinnerSelection(spinner: Spinner, value: String?) {
        if (value != null) {
            val adapter = spinner.adapter
            for (i in 0 until adapter.count) {
                if (adapter.getItem(i).toString() == value) {
                    spinner.setSelection(i)
                    break
                }
            }
        }
    }

}