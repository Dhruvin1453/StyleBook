package com.example.stylebook

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TimePicker
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Update_appointment : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContentView(R.layout.activity_update_appointment)

        val ecid = findViewById<EditText>(R.id.ecid)
        val icon_list =  findViewById<ImageView>(R.id.icon_list)
        val ecname = findViewById<EditText>(R.id.ecname)
        val cservice = findViewById<Spinner>(R.id.cservice)
        val cstyle = findViewById<Spinner>(R.id.cstyle)
        val cdate = findViewById<DatePicker>(R.id.cdate)
        val ctime = findViewById<TimePicker>(R.id.ctime)
        val btnupdate = findViewById<Button>(R.id.btnupdate)
        val icon_home  = findViewById<ImageView>(R.id.icon_home)
        val demo = DataBaseDemo(this)






        ctime.setIs24HourView(true)

        val service = demo.readser()
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item,service)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        cservice.adapter = adapter

        val style = demo.readset()
        val adapter2 = ArrayAdapter(this, android.R.layout.simple_spinner_item,style)
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        cstyle.adapter = adapter2



        var am_pm : String



        btnupdate.setOnClickListener{

            var custName =  ecname.text.toString()
            var custSer =  cservice.selectedItem.toString()
            var custStyle = cstyle.selectedItem.toString()

            var date = cdate.dayOfMonth
            var mont = cdate.month + 1
            var year = cdate.year

            var custDate = "$date/$mont/$year"


            var hour = ctime.hour
            var minute = ctime.minute

            if(hour > 12){

                hour = hour - 12
                am_pm = "PM"
            }else{
                am_pm = "AM"
            }

            var custTime = "$hour:$minute:$am_pm"

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




                val id = ecid.text.toString()

                demo.updatedata(id,custName,custStyle,custSer,charge,custDate,custTime)




            Toast.makeText(this,"Appointment Update Successfully", Toast.LENGTH_LONG).show()

        }


        icon_list.setOnClickListener{


            val  intent = Intent(this,Display_page::class.java)
            startActivity(intent)

        }


        icon_home.setOnClickListener{

            val intent = Intent(this,Homepage::class.java)
            startActivity(intent)

        }




    }
}