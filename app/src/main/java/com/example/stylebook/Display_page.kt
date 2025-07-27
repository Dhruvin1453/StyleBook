package com.example.stylebook
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ListView
import android.widget.SimpleCursorAdapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Display_page : AppCompatActivity() {


   lateinit var  demo : DataBaseDemo


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_display_page)

        val listView1 = findViewById<ListView>(R.id.listview1)
        demo = DataBaseDemo(this)

      val btnshow = findViewById<Button>(R.id.btnshow)
      val btnupdate = findViewById<Button>(R.id.btnupdate)
      val btndelete = findViewById<Button>(R.id.btndelete)
      val eid = findViewById<EditText>(R.id.eid)
      val icon_home = findViewById<ImageView>(R.id.icon_home)
       val icon_add = findViewById<ImageView>(R.id.icon_add)




      var name:String?= intent.getStringExtra("Name")
      var service:String? = intent.getStringExtra("Service")
      var style : String?= intent.getStringExtra("Style")
      var date : String?=  intent.getStringExtra("Date")
      var time : String ?= intent.getStringExtra("Time")



        fun insert(){

             demo.insertData(name,style,service,date,time)

        }

        fun update (){

            eid.visibility = View.VISIBLE
            val id = eid.text.toString()

            demo.updatedata(id,name,style,service,date,time)

        }

        fun delete(){


            eid.visibility = View.VISIBLE
            val id = eid.text.toString()
            demo.deletedata(id)
        }

        fun displaydata() {

            val cursor = demo.readdata()
            val columns = arrayOf(DataBaseDemo.CUS_CNAME,DataBaseDemo.CUS_STY,DataBaseDemo.CUS_SER,DataBaseDemo.CUS_DATE,DataBaseDemo.CUS_TIME)
            val toView = intArrayOf(R.id.tname,R.id.tstyle,R.id.tmoney,R.id.tdate,R.id.ttime)

            val adapter = SimpleCursorAdapter(this,R.layout.list_item,cursor,columns,toView,0)
            //listview

            listView1.adapter = adapter


        }

            btnshow.setOnClickListener{

                 insert()
                displaydata()

            }


           btnupdate.setOnClickListener{

               update()
               displaydata()
           }

        btndelete.setOnClickListener{

            delete()
            displaydata()
        }


        icon_home.setOnClickListener{

            val intent = Intent(this,Homepage::class.java)
            startActivity(intent)

        }


        icon_add.setOnClickListener{

            val intent = Intent(this,apointment_page::class.java)
            startActivity(intent)
        }



        }







    }
