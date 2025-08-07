package com.example.stylebook
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.Button
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


        val updatebutton = findViewById<ImageView>(R.id.updatebutton)
        val btndelete = findViewById<ImageView>(R.id.btndelete)
        val footer = findViewById<BottomNavigationView>(R.id.footer)





            btndelete.setOnClickListener{

                val builder = AlertDialog.Builder(this)
                builder.setTitle("Delete Customer")
                builder.setMessage("Enter Customer Id For Delete :")


                val inpute = EditText(this)
                inpute.hint = "Customer Id :"
                inpute.inputType = InputType.TYPE_CLASS_TEXT


                builder.setView(inpute)


                builder.setPositiveButton("Delete"){ dialog, _ ->

                    val custId = inpute.text.toString().trim()

                    if(custId.isNotEmpty()) {

                      val result =   demo.deletedata(custId)
                        displaydata()

                        if(result > 0){
                                        Toast.makeText(this,"Customer Deleted",Toast.LENGTH_LONG).show()
                        }else{

                                         Toast.makeText(this,"Customer Not Found",Toast.LENGTH_LONG).show()
                        }

                    }

                }


                builder.setNegativeButton("Cancle"){ dialoge, _ ->

                    dialoge.dismiss()
                }


                builder.show()


            }





        footer.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.icon_home -> {

                    val intent = Intent(this,Homepage::class.java)
                    startActivity(intent)
                    true

                }
                R.id.icon_add -> {
                    val intent = Intent(this,apointment_page::class.java)
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

        updatebutton.setOnClickListener{

            val intent = Intent(this,Update_appointment::class.java)
            startActivity(intent)

        }



        }


    fun displaydata() {

        val cursor = demo.readdata()
        val columns = arrayOf(DataBaseDemo.CUS_ID,DataBaseDemo.CUS_CNAME,DataBaseDemo.CUS_STY,DataBaseDemo.CUS_SER,DataBaseDemo.CUS_CHAR,DataBaseDemo.CUS_DATE,DataBaseDemo.CUS_TIME)
        val toView = intArrayOf(R.id.tid,R.id.tname,R.id.tstyle,R.id.tser,R.id.tmoney,R.id.tdate,R.id.ttime)

        val adapter = SimpleCursorAdapter(this,R.layout.list_item,cursor,columns,toView,0)
        //listview

        listView1.adapter = adapter


    }




    }




