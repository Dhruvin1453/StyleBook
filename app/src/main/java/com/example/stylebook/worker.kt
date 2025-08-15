package com.example.stylebook

import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
import androidx.core.widget.addTextChangedListener
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


        wname.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(p0: Editable?) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                val name = wname.text.toString()
                if (name.isEmpty()){

                    wname.error = "Please enter name"
                }else{

                    wname .error = null
                }

            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        })


        wnumber.addTextChangedListener(object : TextWatcher{

            override fun afterTextChanged(p0: Editable?) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                val number = wnumber.text.toString()
                if (number.isEmpty() || number.length != 10 || ! number.all { it.isDigit() }){

                    wnumber.error = "Enter 10 digit mobile number"
                }else{

                    wnumber.error = null
                }

            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        })

        wemail.addTextChangedListener(object  : TextWatcher {
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                val email = wemail.text.toString()
                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){

                    wemail.error = "Enter Valid Email"
                }else{


                    wemail.error = null
                }

            }

            override fun afterTextChanged(p0: Editable?) {}

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        })


        wadress.addTextChangedListener (object  : TextWatcher{

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                val add = wadress.text.toString()
                if (add.isEmpty()){

                    wadress.error = "Please Enter the Adress"
                }else{

                    wadress.error = null
                }

            }

            override fun afterTextChanged(p0: Editable?) {}

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        })


        wcharge.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(p0: Editable?) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val charge = wcharge.text.toString()
                if (charge.isEmpty()){

                    wcharge.error = "Please enter the charge"
                }else{

                    wcharge.error = null
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        })


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



            if (name.isEmpty()){
                Toast.makeText(this,"Please enter name",Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            if (number.isEmpty() || number.length != 10 || ! number.all { it.isDigit() }){
                Toast.makeText(this,"Please enter the number",Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                Toast.makeText(this,"Please enter the email",Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            if (add.isEmpty()){

                Toast.makeText(this,"Please enter the adress",Toast.LENGTH_LONG).show()
             return@setOnClickListener

            }


            if (charge.isEmpty()){


                Toast.makeText(this,"Please enter the charge",Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            if (tvselecttime.text.toString() == "select time"){

                Toast.makeText(this,"Please selsect start time",Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            if(tvSelected.text.toString() == "select time"){

                Toast.makeText(this,"Please selsect end time",Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }


            val result =  demo.insertWor(name,number,email,add,staime,etime,charge,appo,revenu)

            if (result == -1L) {

                Toast.makeText(this, "❌ Insert failed", Toast.LENGTH_SHORT).show()
            } else {
                wname.text.clear()
                wnumber.text.clear()
                wemail.text.clear()
                wadress.text.clear()
                wcharge.text.clear()
                tvselecttime.text = "select time"
                tvSelected.text = "select time"
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