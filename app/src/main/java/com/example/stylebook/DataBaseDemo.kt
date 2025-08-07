package com.example.stylebook

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DataBaseDemo(context: Context) : SQLiteOpenHelper(context,DATABASE_NAME,null,DATABASE_VERSION){

    companion object{

        const val  DATABASE_NAME = "MYDATA"
        const val  DATABASE_VERSION = 8


        const val USER_TABLE = "USER"
        const val USER_ID = "_uid"
        const val USER_NAME = "_uname"
        const val USER_EMAIL = "_uemail"
        const val USER_PHONE = "_uphone"
        const val USER_PASS = "_upass"


        const val TABLE_NAME = "CUST"
        const val CUS_ID = "_id"
        const val CUS_CNAME = "_cname"
        const val CUS_STY = "_style"
        const val CUS_SER = "_servi"
        const val CUS_CHAR = "_charge"
        const val CUS_DATE = "_date"
        const val CUS_TIME = "_time"



        const val SER_ID = "_seid"
        const val SER_NAME ="_srename"
        const val SER_CHAR = "_charge"
        const val SETABLE_NAME = "SERVICE"

        const val STY_ID = "_stid"
        const val STY_NAME = "_stname"
        const val STY_CHAR = "_stcharge"
        const val STTABLE_NAME = "STYLE"


    }

    override fun onCreate(db: SQLiteDatabase?) {

        val utable = "CREATE TABLE IF NOT EXISTS $USER_TABLE($USER_ID INTEGER PRIMARY KEY AUTOINCREMENT,$USER_NAME TEXT,$USER_EMAIL TEXT,$USER_PHONE TEXT,$USER_PASS TEXT)"
        db?.execSQL(utable)

        val creattable = "CREATE TABLE IF NOT EXISTS $TABLE_NAME($CUS_ID INTEGER PRIMARY KEY AUTOINCREMENT,$CUS_CNAME TEXT ,$CUS_STY TEXT,$CUS_SER TEXT,$CUS_CHAR TEXT,$CUS_DATE TEXT,$CUS_TIME TEXT)"
        db?.execSQL(creattable)


        val sertable = "CREATE TABLE IF NOT EXISTS $SETABLE_NAME($SER_ID INTEGER PRIMARY KEY AUTOINCREMENT,$SER_NAME TEXT,$SER_CHAR TEXT)"
        db?.execSQL(sertable)


        val sttable = "CREATE TABLE IF NOT EXISTS $STTABLE_NAME($STY_ID INTEGER PRIMARY KEY AUTOINCREMENT,$STY_NAME TEXT,$STY_CHAR TEXT)"
        db?.execSQL(sttable)



    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {

        val dropqu = "DROP TABLE IF EXISTS $USER_TABLE"
        db?.execSQL(dropqu)


        val dropquery = "DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(dropquery)

        val dropser = "DROP TABLE IF EXISTS $SETABLE_NAME"
        db?.execSQL(dropser)


        val dropsty = "DROP TABLE IF EXISTS $STTABLE_NAME"
        db?.execSQL(dropsty)

        onCreate(db)


 }


    fun userinsert(username:String,userema:String,userphone:String,userpass:String):Boolean{

        val db = writableDatabase
        val value = ContentValues()
        value.put(USER_NAME, username)
        value.put(USER_EMAIL, userema)
        value.put(USER_PHONE, userphone)
        value.put(USER_PASS, userpass)
        val result=  db.insert(USER_TABLE, null, value)

        return result != -1L

    }

    fun checkUser(email: String, pass: String): Boolean {
        val db = this.readableDatabase
        val query = "SELECT * FROM $USER_TABLE WHERE $USER_EMAIL = ? AND $USER_PASS = ?"
        val cursor = db.rawQuery(query, arrayOf(email, pass))
        val exists = cursor.count > 0
        cursor.close()
        db.close()
        return exists
    }


      fun insertData(cname : String ?,cstyle : String? ,cserv : String? ,ccharge : String? ,cdate : String?,ctime : String? ):Long
      {

          val db = writableDatabase
          val value = ContentValues()
          value.put(CUS_CNAME,cname)
          value.put(CUS_STY,cstyle)
          value.put(CUS_SER,cserv)
          value.put(CUS_CHAR,ccharge)
          value.put(CUS_DATE,cdate)
          value.put(CUS_TIME,ctime)

          return db.insert(TABLE_NAME,null,value)

      }


    fun readdata() : Cursor{

        val  db = readableDatabase
        val readquer = "SELECT * FROM $TABLE_NAME"

        return db.rawQuery(readquer,null)

    }


    fun updatedata(id:String ?,cname : String ?,cstyle : String? ,cserv : String? ,ccharge : String? ,cdate : String?,ctime : String? ):Int{

        val db = writableDatabase
        val values = ContentValues()
        values.put(CUS_CNAME,cname)
        values.put(CUS_CNAME,cname)
        values.put(CUS_STY,cstyle)
        values.put(CUS_SER,cserv)
        values.put(CUS_CHAR,ccharge)
        values.put(CUS_DATE,cdate)
        values.put(CUS_TIME,ctime)

        return db.update(TABLE_NAME,values,"$CUS_ID = ?", arrayOf(id.toString()))


    }


    fun deletedata(id:String?):Int{
        val db = writableDatabase
        return db.delete(TABLE_NAME,"$CUS_ID = ?", arrayOf(id.toString()))
    }


    fun readthreedata() : Cursor{

        val db = writableDatabase
        val query = "SELECT * FROM $TABLE_NAME LIMIT 3"

        return db.rawQuery(query,null)


    }





    fun serinsert(sname:String,scharge:String):Long

    {


        val db = writableDatabase
        val value = ContentValues()
        value.put(SER_NAME,sname)
        value.put(SER_CHAR,scharge)
        return db.insert(SETABLE_NAME,null,value)


    }


    fun readser(): List<String>{


        val serviceList = mutableListOf<String>()
        val db = readableDatabase
        val query = "select $SER_NAME from $SETABLE_NAME"
        val cursor =  db.rawQuery(query,null)

         if(cursor.moveToFirst()){

             do {
                 val name = cursor.getString(cursor.getColumnIndexOrThrow(SER_NAME))
                  serviceList.add(name)
             }while (cursor.moveToNext())

         }

        cursor.close()
        db.close()
        return serviceList

    }


    fun styinsert(sename:String,secharge:String):Long

    {


        val db = writableDatabase
        val value = ContentValues()
        value.put(STY_NAME,sename)
        value.put(STY_CHAR,secharge)
        return db.insert(STTABLE_NAME,null,value)


    }


    fun readset(): List<String>{

        val serviceList = mutableListOf<String>()
        val db = readableDatabase
        val query = "select $STY_NAME from $STTABLE_NAME"
        val cursor =  db.rawQuery(query,null)

        if(cursor.moveToFirst()){

            do {
                val name = cursor.getString(cursor.getColumnIndexOrThrow(STY_NAME))
                serviceList.add(name)
            }while (cursor.moveToNext())

        }

        cursor.close()
        db.close()
        return serviceList
    }

    fun readsercharge(ser : String):Cursor{

        val db  = readableDatabase
        val query =  " select $SER_CHAR from $SETABLE_NAME where $SER_NAME = ?"
        return db.rawQuery(query, arrayOf(ser))

    }

    fun readstycharge(sty : String):Cursor{

        val db  = readableDatabase
        val query =  " select $STY_CHAR from $STTABLE_NAME where $STY_NAME = ?"
        return db.rawQuery(query, arrayOf(sty))

    }




}