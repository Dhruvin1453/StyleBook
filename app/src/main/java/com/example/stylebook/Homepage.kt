package com.example.stylebook

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Homepage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

         var  demo:DataBaseDemo


        setContentView(R.layout.activity_homepage)

        val icon_add = findViewById<ImageView>(R.id.icon_add)
        val icon_list = findViewById<ImageView>(R.id.icon_list)
        val view_all = findViewById<Button>(R.id.view_all)


        var dynamicList = findViewById<LinearLayout>(R.id.dynamicList)

        dynamicList.removeAllViews()


        demo = DataBaseDemo(this)

        val cursor = demo.readthreedata()


        if(cursor.moveToFirst()){

            do{


                val name = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseDemo.CUS_CNAME))
                val style = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseDemo.CUS_STY))
                val service = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseDemo.CUS_SER))
                val date = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseDemo.CUS_DATE))
                val time = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseDemo.CUS_TIME))

               val itemeView = layoutInflater.inflate(R.layout.list_item,dynamicList,false)


                itemeView.findViewById<TextView>(R.id.tname).text = name
                itemeView.findViewById<TextView>(R.id.tstyle).text = style
                itemeView.findViewById<TextView>(R.id.tmoney).text = service
                itemeView.findViewById<TextView>(R.id.tdate).text = date
                itemeView.findViewById<TextView>(R.id.ttime).text = time


                dynamicList.addView(itemeView)

            }while (cursor.moveToNext())


        }

        cursor.close()


        view_all.setOnClickListener{

            val intent = Intent(this,Display_page::class.java)
            startActivity(intent)
        }


       icon_add.setOnClickListener{

           val intent = Intent(this,apointment_page::class.java)
           startActivity(intent)
       }

       icon_list.setOnClickListener{

           val intent = Intent(this,Display_page::class.java)
           startActivity(intent)

       }




    }
}