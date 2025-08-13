package com.example.stylebook

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.widget.EditText
import android.widget.ImageView
import android.widget.ListView
import android.widget.SimpleCursorAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.bottomnavigation.BottomNavigationView

class stafff : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        setContentView(R.layout.activity_stafff)



        displaydata()


        val btnadd = findViewById<ImageView>(R.id.btnadd)
        val footer = findViewById<BottomNavigationView>(R.id.footer)










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

        btnadd.setOnClickListener {

            val intent = Intent(this, worker::class.java)
            startActivity(intent)

        }



    }


    fun displaydata() {


        val demo = DataBaseDemo(this)
        val listView1 = findViewById<ListView>(R.id.listview1)

        val cursor = demo.readworkerdata()
        val columns = arrayOf(DataBaseDemo.WOR_NAME,DataBaseDemo.WOR_MOB,DataBaseDemo.WOR_EMA,DataBaseDemo.WOR_STIME,DataBaseDemo.WOR_ETIME,DataBaseDemo.WOR_ADD,DataBaseDemo.WOR_APP,DataBaseDemo.WOR_REV)
        val toView = intArrayOf(R.id.tname,R.id.tphone,R.id.temail,R.id.tstime,R.id.tetime,R.id.tadd,R.id.tapp,R.id.treve)

        val adapter = SimpleCursorAdapter(this,R.layout.workerlist,cursor,columns,toView,0)
        //listview

        listView1.adapter = adapter


    }




}
