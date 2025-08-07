package com.example.stylebook
import android.content.Intent
import com.example.stylebook.R
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

       val email = findViewById<EditText>(R.id.email)
       val  pass = findViewById<EditText>(R.id.pass)
       val login = findViewById<Button>(R.id.login)
       val signup = findViewById<TextView>(R.id.signup)


       login.setOnClickListener{

           var email = email.text.toString()
           var pass = pass.text.toString()

           if(email.isNotEmpty() && pass.isNotEmpty()){

               if(checkUser(email,pass)){

                   Toast.makeText(this,"Login Successfully",Toast.LENGTH_LONG).show()
                   val intent = Intent(this,Homepage::class.java)
                   startActivity(intent)

               }else{

                     Toast.makeText(this,"Wrong Email Or Password",Toast.LENGTH_LONG).show()
               }


           }else{

               Toast.makeText(this,"Please Enter Both Fields",Toast.LENGTH_LONG).show()
           }

       }

        signup.setOnClickListener{

            val intent = Intent(this,registration_page::class.java)
            startActivity(intent)
        }



    }
    fun checkUser(email: String, pass: String): Boolean {
        val dbHelper = DataBaseDemo(this)
        return dbHelper.checkUser(email, pass)
    }

}