package com.example.stylebook

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TimePicker
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class registration_page : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration_page)


       val ename = findViewById<EditText>(R.id.ename)
       val eemail = findViewById<EditText>(R.id.eemail)
       val enumber = findViewById<EditText>(R.id.enumber)
       val epass = findViewById<EditText>(R.id.epass)
        val ecopass = findViewById<EditText>(R.id.ecopass)
        val esign = findViewById<Button>(R.id.esign)
        val demo:DataBaseDemo = DataBaseDemo(this)


        esign.setOnClickListener{


            var uname = ename.text.toString()
            var email = eemail.text.toString()
            var number = enumber.text.toString()
            var pass = epass.text.toString()
            var copss = ecopass.text.toString()



          if(uname.isNotEmpty() && email.isNotEmpty() && number.isNotEmpty() && pass.isNotEmpty() && copss.isNotEmpty()){

              if (copss.equals(pass)){

                 var isInserted=  demo.userinsert(uname,email,number,pass)

                  if (isInserted){

                      Toast.makeText(this,"Sign Up Successfully !!",Toast.LENGTH_LONG).show()

                      val intent = Intent(this,MainActivity::class.java)
                      startActivity(intent)

                  }


              } else{

                  Toast.makeText(this,"Please Enter Same Password",Toast.LENGTH_LONG).show()
              }

          }else{

              Toast.makeText(this,"Please Enter All The Fields",Toast.LENGTH_LONG).show()

          }





        }

    }
}