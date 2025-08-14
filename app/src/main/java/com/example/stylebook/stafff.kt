package com.example.stylebook

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.InputType
import android.widget.EditText
import android.widget.ImageView
import android.widget.ListView
import android.widget.SimpleCursorAdapter
import android.widget.TextView
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
        val columns = arrayOf(DataBaseDemo.WOR_NAME,DataBaseDemo.WOR_MOB,DataBaseDemo.WOR_EMA,DataBaseDemo.WOR_STIME,DataBaseDemo.WOR_ETIME,DataBaseDemo.WOR_ADD,DataBaseDemo.WOR_APP,DataBaseDemo.WOR_REV,DataBaseDemo.WOR_MOB,DataBaseDemo.WOR_ID)
        val toView = intArrayOf(R.id.tname,R.id.tphone,R.id.temail,R.id.tstime,R.id.tetime,R.id.tadd,R.id.tapp,R.id.treve,R.id.call_icons,R.id.btn_delete)   // button mapping







        val adapter = SimpleCursorAdapter(this,R.layout.workerlist,cursor,columns,toView,0)

        adapter.setViewBinder { view, c, i ->

            if (view.id == R.id.call_icons) {
                val phone = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseDemo.WOR_MOB))
                view.setOnClickListener {
                    val intent = Intent(Intent.ACTION_DIAL)
                    intent.data = Uri.parse("tel:$phone")
                    startActivity(intent)
                }
                true
            }    else if (view.id == R.id.btn_delete) {
                val workerId = c.getString(c.getColumnIndexOrThrow(DataBaseDemo.WOR_ID))
                view.setOnClickListener {
                    val result = demo.deleteworker(workerId)
                    if (result > 0) {
                        Toast.makeText(this, "Worker Deleted", Toast.LENGTH_SHORT).show()
                        displaydata() // refresh list after delete
                    } else {
                        Toast.makeText(this, "Worker Not Found", Toast.LENGTH_SHORT).show()
                    }
                }
                true
            } else {
                false
            }

        }

        listView1.adapter = adapter


    }




}
