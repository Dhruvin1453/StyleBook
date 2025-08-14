package com.example.stylebook
import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.Button
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

class Display_page : AppCompatActivity() {


   lateinit var  demo : DataBaseDemo
   lateinit var listView1 : ListView


    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_page)

        listView1 = findViewById(R.id.listview1)

        demo = DataBaseDemo(this)

        displaydata()




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




        }


    fun displaydata() {

        val cursor = demo.readdata()
        val columns = arrayOf(
            DataBaseDemo.CUS_CNAME,
            DataBaseDemo.CUS_NUM,
            DataBaseDemo.CUS_STY,
            DataBaseDemo.CUS_SER,
            DataBaseDemo.CUS_WOR,
            DataBaseDemo.CUS_CHAR,
            DataBaseDemo.CUS_DATE,
            DataBaseDemo.CUS_TIME,
            DataBaseDemo.CUS_NUM,
            DataBaseDemo.CUS_ID,
            DataBaseDemo.CUS_ID

        )
        val toView = intArrayOf(
            R.id.tname,
            R.id.tphone,
            R.id.tstyle,
            R.id.tser,
            R.id.worker,
            R.id.tmoney,
            R.id.tdate,
            R.id.ttime,
            R.id.btn_call,
            R.id.btn_delete,
            R.id.btn_edit
        )

        val adapter = SimpleCursorAdapter(this, R.layout.list_item, cursor, columns, toView, 0)

        adapter.setViewBinder { view, c, i ->

            if (view.id == R.id.btn_call) {
                val phone = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseDemo.CUS_NUM))
                view.setOnClickListener {
                    val intent = Intent(Intent.ACTION_DIAL)
                    intent.data = Uri.parse("tel:$phone")
                    startActivity(intent)
                }
                true
            }    else if (view.id == R.id.btn_delete) {
                val custId = c.getString(c.getColumnIndexOrThrow(DataBaseDemo.CUS_ID))
                view.setOnClickListener {
                    val result = demo.deletedata(custId)
                    if (result > 0) {
                        Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show()
                        displaydata() // refresh list after delete
                    } else {
                        Toast.makeText(this, "Customer Not Found", Toast.LENGTH_SHORT).show()
                    }
                }
                true
            }

            else if (view.id == R.id.btn_edit) {
                val custId = c.getString(c.getColumnIndexOrThrow(DataBaseDemo.CUS_ID))
                val name = c.getString(c.getColumnIndexOrThrow(DataBaseDemo.CUS_CNAME))
                val phone = c.getString(c.getColumnIndexOrThrow(DataBaseDemo.CUS_NUM))
                val style = c.getString(c.getColumnIndexOrThrow(DataBaseDemo.CUS_STY))
                val service = c.getString(c.getColumnIndexOrThrow(DataBaseDemo.CUS_SER))
                val worker = c.getString(c.getColumnIndexOrThrow(DataBaseDemo.CUS_WOR))
                val price = c.getString(c.getColumnIndexOrThrow(DataBaseDemo.CUS_CHAR))
                val date = c.getString(c.getColumnIndexOrThrow(DataBaseDemo.CUS_DATE))
                val time = c.getString(c.getColumnIndexOrThrow(DataBaseDemo.CUS_TIME))

                view.setOnClickListener {
                    val intent = Intent(this, Update_appointment::class.java)
                    intent.putExtra("id", custId)
                    intent.putExtra("name", name)
                    intent.putExtra("phone", phone)
                    intent.putExtra("style", style)
                    intent.putExtra("service", service)
                    intent.putExtra("worker", worker)
                    intent.putExtra("price", price)
                    intent.putExtra("date", date)
                    intent.putExtra("time", time)
                    startActivity(intent)
                }
                true
            }

            else{
                false
            }

        }

        listView1.adapter = adapter

    }
    }




