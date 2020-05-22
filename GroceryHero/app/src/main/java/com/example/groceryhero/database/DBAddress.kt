package com.example.groceryhero.database

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.groceryhero.app.MyActivity
import com.example.groceryhero.model.AddData
import com.example.groceryhero.model.Address
import com.example.groceryhero.model.AddressData
import com.example.groceryhero.model.item

class DBAddress(): SQLiteOpenHelper(MyActivity.instance, "AddressDB",null,1) {
    var dbWritable:SQLiteDatabase = writableDatabase
    companion object{
        //create column name and table names here
        const val TABLE_NAME = "AddressDB"
        const val ROW_ID = "rowId"
        const val COLUMN_CITY = "city"
        const val COLUMN_HOUSENO= "houseNo"
        const val COLUMN_COUNTRY = "country"
        const val COLUMN_ZIPCODE = "zipCode"
        const val COLUMN_TYPE = "type"
        const val COLUMN_STREET = "streetName"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        var createTable = "create table $TABLE_NAME(" +
                "$ROW_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COLUMN_STREET char(50),"+
                "$COLUMN_CITY char(50),"  +
                "$COLUMN_HOUSENO char(50)," +
                "$COLUMN_COUNTRY char(50)," +
                "$COLUMN_ZIPCODE char(50)," +
                "$COLUMN_TYPE char(50))"
        db?.execSQL(createTable)
        Log.d("DBLog", "Table Created")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val dropTable = "drop table $TABLE_NAME"
        db?.execSQL(dropTable)
        onCreate(db)
        Log.d("DBLog", "onUpgrade")
    }

    fun addAddress(item:AddData) {
        var content = ContentValues()
        content.put(COLUMN_STREET,item.streetName)
        content.put(COLUMN_CITY,item.city)
        content.put(COLUMN_HOUSENO,item.houseNo)
        content.put(COLUMN_COUNTRY, item.location)
        content.put(COLUMN_ZIPCODE, item.pincode)
        content.put(COLUMN_TYPE, item.type)
        dbWritable.insert(TABLE_NAME, null,content)
    }

//    fun deleteAddress(zip:String) {
//        var whereClause = "$COLUMN_ZIPCODE=?"
//        var whereArgs = arrayOf(zip)
//        dbWritable.delete(TABLE_NAME, whereClause, whereArgs)
//    }

    fun readData():ArrayList<AddData> {
        var mList:ArrayList<AddData> = ArrayList()
        var columns = arrayOf(
            COLUMN_STREET,
            COLUMN_CITY,
            COLUMN_HOUSENO,
            COLUMN_COUNTRY,
            COLUMN_ZIPCODE,
            COLUMN_TYPE
        )

        var cursor = dbWritable.query(TABLE_NAME, columns, null, null, null, null, null)

        if(cursor !=null && cursor.moveToFirst()) {
            do{
                var street = cursor.getString(cursor.getColumnIndex(COLUMN_STREET))
                var city = cursor.getString(cursor.getColumnIndex(COLUMN_CITY))
                var houseNo = cursor.getString(cursor.getColumnIndex(COLUMN_HOUSENO))
                var country = cursor.getString(cursor.getColumnIndex(COLUMN_COUNTRY))
                var zip = cursor.getString(cursor.getColumnIndex(COLUMN_ZIPCODE))
                var type = cursor.getString(cursor.getColumnIndex(COLUMN_TYPE))
                var item = AddData(city = city, houseNo = houseNo, location = country, pincode = zip.toInt(), type = type, streetName = street)
                mList.add(item)
            }while(cursor.moveToNext())
        }
        cursor.close()
        return mList
    }

    fun deleteTable() {
        var deleteTable = "drop table ${DBHelper.TABLE_NAME}"
        dbWritable.execSQL(deleteTable)
        onCreate(dbWritable)
    }


    fun isPopulated():Boolean {

        var query = "Select * from $TABLE_NAME"
        var cursor =dbWritable.rawQuery(query, null)
        var count = cursor.count

        return count != 0
    }

    fun deleteAddress(id:String) {
        var whereClause = "$ROW_ID=?"
        var whereArgs = arrayOf(id)
        dbWritable.delete(TABLE_NAME, whereClause, whereArgs)
    }

    fun deleteData() {
        dbWritable.delete(TABLE_NAME, null, null)
    }

    fun returnSingleData():AddData {
        var item = AddData(0,"","","","","","",0,"","","")
        var columns = arrayOf(
            COLUMN_STREET,
            COLUMN_CITY,
            COLUMN_HOUSENO,
            COLUMN_COUNTRY,
            COLUMN_ZIPCODE,
            COLUMN_TYPE
        )

        var cursor = dbWritable.query(TABLE_NAME, columns, null, null, null, null, null)

        if(cursor !=null && cursor.moveToFirst()) {
            do{
                var street = cursor.getString(cursor.getColumnIndex(COLUMN_STREET))
                var city = cursor.getString(cursor.getColumnIndex(COLUMN_CITY))
                var houseNo = cursor.getString(cursor.getColumnIndex(COLUMN_HOUSENO))
                var country = cursor.getString(cursor.getColumnIndex(COLUMN_COUNTRY))
                var zip = cursor.getString(cursor.getColumnIndex(COLUMN_ZIPCODE))
                var type = cursor.getString(cursor.getColumnIndex(COLUMN_TYPE))
                item = AddData(city = city, houseNo = houseNo, location = country, pincode = zip.toInt(), type = type, streetName = street)
                return item
            }while(cursor.moveToNext())
        }
        cursor.close()
        return item
    }

}