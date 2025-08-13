package com.example.stylebook

import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.Calendar

class worker : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_worker)



        val wname = findViewById<EditText>(R.id.wname)
        val wnumber = findViewById<EditText>(R.id.wnumber)
        val wadress = findViewById<EditText>(R.id.wadress)
        val wemail = findViewById<EditText>(R.id.wemail)


        val tstart = findViewById<LinearLayout>(R.id.tstart)
        val tvselecttime = findViewById<TextView>(R.id.tvselecttime)


        val tend = findViewById<LinearLayout>(R.id.tend)
        val tvSelected = findViewById<TextView>(R.id.tvSelected)

        val wcharge = findViewById<EditText>(R.id.wcharge)

        val btninsert = findViewById<Button>(R.id.btninsert)
        val footer = findViewById<BottomNavigationView>(R.id.footer)
        val demo = DataBaseDemo(this)



        tstart.setOnClickListener {
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
                tvselecttime.text = selectedTime
            }, hour, minute, false)

            timePicker.show()
        }


        tend.setOnClickListener {
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

                val time = String.format("%02d:%02d %s", hourFormatted, selectedMinute, amPm)
                tvSelected.text = time
            }, hour, minute, false)

            timePicker.show()
        }





        btninsert.setOnClickListener{


            val name = wname.text.toString()
            val number = wnumber.text.toString()
            val email = wemail.text.toString()
            val add = wadress.text.toString()
            val staime = tvselecttime.text.toString()
            val etime = tvSelected.text.toString()
            val charge = wcharge.text.toString()
            val appo = "0"
            val revenu = "0"

            val result =  demo.insertWor(name,number,email,add,staime,etime,charge,appo,revenu)

            if (result == -1L) {
                Toast.makeText(this, "❌ Insert failed", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "✅ Insert successful", Toast.LENGTH_SHORT).show()
            }

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