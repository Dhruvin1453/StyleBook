package com.example.stylebook

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DataBaseDemo(context: Context) : SQLiteOpenHelper(context,DATABASE_NAME,null,DATABASE_VERSION){

    companion object{

        const val  DATABASE_NAME = "MYDATA"
        const val  DATABASE_VERSION = 3
        const val TABLE_NAME = "CUST"
        const val CUS_ID = "_id"
        const val CUS_CNAME = "_cname"
        const val CUS_STY = "_style"
        const val CUS_SER = "_servi"
        const val CUS_DATE = "_date"
        const val CUS_TIME = "_time"

    }

    override fun onCreate(db: SQLiteDatabase?) {

        val creattable = "CREATE TABLE IF NOT EXISTS $TABLE_NAME($CUS_ID INTEGER PRIMARY KEY AUTOINCREMENT,$CUS_CNAME TEXT ,$CUS_STY TEXT,$CUS_SER TEXT,$CUS_DATE TEXT,$CUS_TIME TEXT)"
        db?.execSQL(creattable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {

        val dropquery = "DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(dropquery)
        onCreate(db)


    }


      fun insertData(cname : String ?,cstyle : String? ,cserv : String? ,cdate : String?,ctime : String? ):Long
      {

          val db = writableDatabase
          val value = ContentValues()
          value.put(CUS_CNAME,cname)
          value.put(CUS_STY,cstyle)
          value.put(CUS_SER,cserv)
          value.put(CUS_DATE,cdate)
          value.put(CUS_TIME,ctime)

          return db.insert(TABLE_NAME,null,value)

      }


    fun readdata() : Cursor{

        val  db = readableDatabase
        val readquer = "SELECT * FROM $TABLE_NAME"

        return db.rawQuery(readquer,null)

    }


    fun updatedata(id:String ?,cname : String ?,cstyle : String? ,cserv : String? ,cdate : String?,ctime : String? ):Int{

        val db = writableDatabase
        val values = ContentValues()
        values.put(CUS_CNAME,cname)
        values.put(CUS_CNAME,cname)
        values.put(CUS_STY,cstyle)
        values.put(CUS_SER,cserv)
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
        val query = "SELECT * FROM $TABLE_NAME"

        return db.rawQuery(query,null)


    }


}