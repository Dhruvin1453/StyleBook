package com.example.stylebook

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener

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

        var uname = ename.text.toString()
        var email = eemail.text.toString().trim()
        var number = enumber.text.toString().trim()
        var pass = epass.text.toString().trim()
        var copss = ecopass.text.toString().trim()




        ename.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                val uname = ename.text.toString().trim()
                if(uname.isEmpty() || uname.length < 2){

                    ename.error = "Enater Valid Name"

                }else{
                    ename.error = null
                }

            }

        })


        eemail.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val email = eemail.text.toString().trim()

                when {
                    !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                        eemail.error = "Enter a Valid Email"
                    }
                    demo.isEmailExists(email) -> {
                        eemail.error = "Email Already Exists"
                    }
                    else -> {
                        eemail.error = null
                    }
                }
            }
        })





        enumber.addTextChangedListener(object : TextWatcher{

            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                var number = enumber.text.toString().trim()
                if(number.length!= 10 || !number.all { it.isDigit() } ){

                    enumber.error = "Enter Valid 10 digit Number"

                }else{
                    enumber.error = null
                }
            }
        })



        epass.addTextChangedListener(object : TextWatcher {

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                var pass = epass.text.toString().trim()
                if (pass.length <6 || !pass.any {it.isDigit()}){

                    epass.error = "Password must be 6 character and contain a number "
                }
                else{
                    epass.error = null
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
        })



        ecopass.addTextChangedListener(object : TextWatcher{

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                var pass = epass.text.toString().trim()
                var copss = ecopass.text.toString().trim()

                if (pass != copss){

                    ecopass.error = "Password do not match"
                }
                else{
                    ecopass.error = null
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
        })



        esign.setOnClickListener{

            val uname = ename.text.toString()
            var email = eemail.text.toString().trim()
            var number = enumber.text.toString().trim()
            var pass = epass.text.toString().trim()
            var copss = ecopass.text.toString().trim()

            if (uname.isEmpty() || uname.length < 2) {

                return@setOnClickListener
            }


            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {

                return@setOnClickListener
            }
            if (demo.isEmailExists(email)) {

                return@setOnClickListener
            }


            if (number.length != 10 || !number.all { it.isDigit() }) {

                return@setOnClickListener
            }

            if (pass.length <6 || !pass.any {it.isDigit()}){

                return@setOnClickListener
            }


            if (pass != copss) {

                return@setOnClickListener
            }

            val isInserted = demo.userinsert(uname,email,number,pass)
            if (isInserted){


                Toast.makeText(this,"Sign  Successfully !!",Toast.LENGTH_LONG).show()
                val intent = Intent(this,MainActivity::class.java)
                startActivity(intent)
            }else{

                Toast.makeText(this,"Sign Up Failed.Try again.",Toast.LENGTH_LONG).show()
            }




        }

    }
}