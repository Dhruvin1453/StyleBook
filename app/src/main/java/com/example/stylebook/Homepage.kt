package com.example.stylebook

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.setPadding
import androidx.recyclerview.widget.RecyclerView.Orientation
import com.google.android.material.bottomnavigation.BottomNavigationView

class Homepage : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


         var  demo:DataBaseDemo


        setContentView(R.layout.activity_homepage)

        val footer = findViewById<BottomNavigationView>(R.id.footer)
        val editbutton = findViewById<CardView>(R.id.editbutton)
        val btnaddapointment = findViewById<CardView>(R.id.btnaddapointment)
        val btndelete = findViewById<CardView>(R.id.btndelete)
        val addservice = findViewById<CardView>(R.id.addservice)
        val btnaddstyle = findViewById<CardView>(R.id.btnaddstyle)
        val view_all = findViewById<Button>(R.id.view_all)
        demo = DataBaseDemo(this)

        updatelist()

        view_all.setOnClickListener{
            val intent = Intent(this,Display_page::class.java)
            startActivity(intent)
        }

        editbutton.setOnClickListener{
            val intent = Intent(this,Update_appointment::class.java)
            startActivity(intent)

        }

        btnaddapointment.setOnClickListener{

            val intent = Intent(this,apointment_page::class.java)
            startActivity(intent)
        }

            btndelete.setOnClickListener{

                val builder = AlertDialog.Builder(this)
                builder.setTitle("Delete Customer")
                builder.setMessage("Enter Customer Id For Delete :")


                val inpute = EditText(this)
                inpute.hint = "Customer Id :"
                inpute.inputType = InputType.TYPE_CLASS_TEXT
                inpute.setPadding(50,50,50,50)



                builder.setView(inpute)


                builder.setPositiveButton("Delete"){ dialog, _ ->

                    val custId = inpute.text.toString().trim()

                    if(custId.isNotEmpty()) {

                        val result =   demo.deletedata(custId)

                        if(result > 0){
                            updatestat()
                            updatelist()
                            Toast.makeText(this,"Customer Deleted", Toast.LENGTH_LONG).show()
                        }else{

                            Toast.makeText(this,"Customer Not Found", Toast.LENGTH_LONG).show()
                        }

                    }

                }


                builder.setNegativeButton("Cancle"){ dialoge, _ ->

                    dialoge.dismiss()
                }


                builder.show()


            }


        addservice.setOnClickListener{

           val builder = AlertDialog.Builder(this)
           builder.setTitle("Add Services ")

           val layout = LinearLayout(this)
           layout.orientation=LinearLayout.VERTICAL
            layout.setPadding(50,50,50,50)


            val serinpute = EditText(this)
            serinpute.hint = "Enter Services"
            serinpute.inputType = InputType.TYPE_CLASS_TEXT
            layout.addView(serinpute)

            val moneyinpute = EditText(this)
            moneyinpute.hint = "Enter Charges"
            moneyinpute.inputType = InputType.TYPE_CLASS_TEXT
            layout.addView(moneyinpute)


            builder.setView(layout)

            builder.setPositiveButton("Add"){ dialoge, _ ->

                val serviceName = serinpute.text.toString().trim()
                val charge = moneyinpute.text.toString().trim()

                if (serviceName.isNotEmpty() && charge.isNotEmpty()) {

                    demo.serinsert(serviceName, charge) // pass string values, not EditText

                } else {

                    Toast.makeText(this, "Please enter both service and charge", Toast.LENGTH_SHORT).show()

                }



            }

            builder.setNegativeButton("Cancle"){ dialoge, _ ->

                dialoge.dismiss()
            }

           builder.show()


        }

        btnaddstyle.setOnClickListener{
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Add Style")

            val layout = LinearLayout(this)
            layout.orientation=LinearLayout.VERTICAL
            layout.setPadding(50,50,50,50)



            val styinpute = EditText(this)
            styinpute.hint = "Enter Style"
            styinpute.inputType = InputType.TYPE_CLASS_TEXT
            layout.addView(styinpute)


            val moneyinpute = EditText(this)
            moneyinpute.hint = "Enter Charges"
            moneyinpute.inputType = InputType.TYPE_CLASS_TEXT
            layout.addView(moneyinpute)


            builder.setView(layout)

            builder.setPositiveButton("Add"){ dialoge, _ ->


                val styName = styinpute.text.toString().trim()
                val charge = moneyinpute.text.toString().trim()

                if (styName.isNotEmpty() && charge.isNotEmpty()) {

                    demo.styinsert(styName, charge) // pass string values, not EditText

                } else {

                    Toast.makeText(this, "Please enter both service and charge", Toast.LENGTH_SHORT).show()

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





         updatestat()

        }



    override fun onResume() {
        super.onResume()
        updatestat()
       updatelist()
    }

    private fun updatestat() {

        val tapointments = findViewById<TextView>(R.id.tapointments)
        val trevanue = findViewById<TextView>(R.id.trevanue)

       val demo:DataBaseDemo = DataBaseDemo(this)
        val db= demo.readableDatabase

        val cursor1 = db.rawQuery("select count(*) from CUST",null)
        cursor1.moveToFirst()
        var appointmments = cursor1.getInt(0)
        cursor1.close()


        val cursor2 = db.rawQuery("SELECT SUM(_charge) FROM CUST",null)
        cursor2.moveToFirst()
        var totalcharge = cursor2.getDouble(0)
        cursor2.close()


        tapointments.text = "$appointmments"
        trevanue.text = "$totalcharge"
    }

    private fun updatelist() {
        var dynamicList = findViewById<LinearLayout>(R.id.dynamicList)
        dynamicList.removeAllViews()

        val demo:DataBaseDemo
        demo = DataBaseDemo(this)

        val cursor = demo.readthreedata()


        if(cursor.moveToFirst()){

            do{

                val id = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseDemo.CUS_ID))
                val name = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseDemo.CUS_CNAME))
                val style = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseDemo.CUS_STY))
                val service = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseDemo.CUS_SER))
                val charges = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseDemo.SER_CHAR))
                val date = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseDemo.CUS_DATE))
                val time = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseDemo.CUS_TIME))

                val itemeView = layoutInflater.inflate(R.layout.list_item,dynamicList,false)

                itemeView.findViewById<TextView>(R.id.tid).text = id
                itemeView.findViewById<TextView>(R.id.tname).text = name
                itemeView.findViewById<TextView>(R.id.tstyle).text = style
                itemeView.findViewById<TextView>(R.id.tser).text = service
                itemeView.findViewById<TextView>(R.id.tmoney).text = charges
                itemeView.findViewById<TextView>(R.id.tdate).text = date
                itemeView.findViewById<TextView>(R.id.ttime).text = time


                dynamicList.addView(itemeView)

            }while (cursor.moveToNext())


        }

        cursor.close()
    }
}
