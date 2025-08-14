package com.example.stylebook

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
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

        val btnaddapointment = findViewById<CardView>(R.id.btnaddapointment)

        val addservice = findViewById<CardView>(R.id.addservice)
        val btnaddstyle = findViewById<CardView>(R.id.btnaddstyle)
        val view_all = findViewById<Button>(R.id.view_all)
        val btnaddtsaff = findViewById<CardView>(R.id.btnaddtsaff)
        demo = DataBaseDemo(this)

        updatelist()

        view_all.setOnClickListener{
            val intent = Intent(this,Display_page::class.java)
            startActivity(intent)
        }



        btnaddapointment.setOnClickListener{

            val intent = Intent(this,apointment_page::class.java)
            startActivity(intent)
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


        btnaddtsaff.setOnClickListener{

            val intent = Intent(this,worker::class.java)
            startActivity(intent)

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
                val no = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseDemo.CUS_NUM))
                val style = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseDemo.CUS_STY))
                val service = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseDemo.CUS_SER))
                val worker = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseDemo.CUS_WOR))
                val charges = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseDemo.SER_CHAR))
                val date = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseDemo.CUS_DATE))
                val time = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseDemo.CUS_TIME))

                val itemeView = layoutInflater.inflate(R.layout.list_item,dynamicList,false)


                itemeView.findViewById<TextView>(R.id.tname).text = name
                itemeView.findViewById<TextView>(R.id.tphone).text = no
                itemeView.findViewById<TextView>(R.id.tstyle).text = style
                itemeView.findViewById<TextView>(R.id.tser).text = service
                itemeView.findViewById<TextView>(R.id.worker).text = worker
                itemeView.findViewById<TextView>(R.id.tmoney).text = charges
                itemeView.findViewById<TextView>(R.id.tdate).text = date
                itemeView.findViewById<TextView>(R.id.ttime).text = time

                itemeView.findViewById<ImageView>(R.id.btn_call).setOnClickListener {
                    val intent = Intent(Intent.ACTION_DIAL)
                    intent.data = Uri.parse("tel:$no")
                    startActivity(intent)
                }


                itemeView.findViewById<Button>(R.id.btn_delete).setOnClickListener {
                    val result = demo.deletedata(id)
                    if (result > 0) {
                        Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show()
                        updatelist()
                    } else {
                        Toast.makeText(this, "Customer Not Found", Toast.LENGTH_SHORT).show()
                    }
                }

                itemeView.findViewById<Button>(R.id.btn_edit).setOnClickListener {
                    val intent = Intent(this, Update_appointment::class.java)
                    intent.putExtra("id", id)
                    intent.putExtra("name", name)
                    intent.putExtra("phone", no)
                    intent.putExtra("style", style)
                    intent.putExtra("service", service)
                    intent.putExtra("worker", worker)
                    intent.putExtra("price", charges)
                    intent.putExtra("date", date)
                    intent.putExtra("time", time)
                    startActivity(intent)
                }


                dynamicList.addView(itemeView)

            }while (cursor.moveToNext())


        }

        cursor.close()
    }
}
