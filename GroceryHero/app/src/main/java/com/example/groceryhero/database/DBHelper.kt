package com.example.groceryhero.database

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.groceryhero.app.MyActivity
import com.example.groceryhero.model.OrderSummary
import com.example.groceryhero.model.ProductsDB

class DBHelper(): SQLiteOpenHelper(MyActivity.instance, "Database",null,1) {


    var dbWritable:SQLiteDatabase = writableDatabase
    companion object{
        //create column name and table names here
        const val TABLE_NAME = "cartDB"
        const val ROW_ID = "rowId"
        const val PRODUCT_NAME = "productName"
        const val PRODUCT_PRICE = "price"
        const val PRODUCT_IMAGE = "image"
        const val PRODUCT_QUANTITY = "quantity"
        const val PRODUCT_MRP = "mrp"
    }
    override fun onCreate(db: SQLiteDatabase?) {
        //Create Table, called only once
        var createTable = "create table $TABLE_NAME(" +
                "$ROW_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$PRODUCT_NAME char(50), " +
                "$PRODUCT_PRICE char(50), " +
                "$PRODUCT_IMAGE char(50), " +
                "$PRODUCT_QUANTITY char(50)," +
                "$PRODUCT_MRP char(50))"
        db?.execSQL(createTable)
        Log.d("STLog", "onCreate")

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val dropTable = "drop table $TABLE_NAME"
        db?.execSQL(dropTable)
        onCreate(db)
        Log.d("STLog", "onUpgrade")
    }

    fun addProduct(item:ProductsDB, quantity:Int) {

        var content = ContentValues()
        content.put(PRODUCT_NAME, item.name)
        content.put(PRODUCT_PRICE, item.price)
        content.put(PRODUCT_IMAGE, item.image)
        content.put(PRODUCT_QUANTITY, quantity)
        content.put(PRODUCT_MRP, item.mrp)
        dbWritable.insert(TABLE_NAME,null,content)
    }

    fun updateProduct(item:String, qty:Int) {
        var whereClause = "$PRODUCT_NAME=?"
        var whereArgs = arrayOf(item)
        var contentValues = ContentValues()
        //change data
        contentValues.put(PRODUCT_QUANTITY, qty)
        dbWritable.update(TABLE_NAME, contentValues, whereClause,whereArgs)
    }

    fun deleteProduct(name:String) {
        var whereClause = "$PRODUCT_NAME=?"
        var whereArgs = arrayOf(name.toString())
        dbWritable.delete(TABLE_NAME, whereClause,whereArgs)
    }

    fun readData(): ArrayList<ProductsDB> {
        var mList:ArrayList<ProductsDB> = ArrayList()
        var columns = arrayOf(
            PRODUCT_NAME,
            PRODUCT_PRICE,
            PRODUCT_IMAGE,
            PRODUCT_QUANTITY,
            PRODUCT_MRP
        )
        var cursor = dbWritable.query(TABLE_NAME,columns, null, null, null, null, null)

        if(cursor !=null && cursor.moveToFirst()) {
            do {
                var name = cursor.getString(cursor.getColumnIndex(PRODUCT_NAME))
                var price = cursor.getString(cursor.getColumnIndex(PRODUCT_PRICE))
                var image = cursor.getString(cursor.getColumnIndex(PRODUCT_IMAGE))
                var quantity = cursor.getString(cursor.getColumnIndex(PRODUCT_QUANTITY))
                var mrp  = cursor.getString(cursor.getColumnIndex(PRODUCT_MRP))
                var item = ProductsDB(name, price, image, quantity.toInt(), mrp.toDouble())
                mList.add(item)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return mList

    }

    fun deleteTable() {
        var deleteTable = "drop table $TABLE_NAME"
        dbWritable.execSQL(deleteTable)
        onCreate(dbWritable)
    }


    fun isItemInCart(item:String):Boolean {
        val query = "Select * from $TABLE_NAME where $PRODUCT_NAME=?"
        val cursor = dbWritable.rawQuery(query, arrayOf(item))
        var count = cursor.count
        return count != 0
    }

    fun itemInCartQuantity(item:String):Int {
        var quantity = 0
        val query = "Select * from $TABLE_NAME where $PRODUCT_NAME=?"
        val cursor = dbWritable.rawQuery(query,arrayOf(item))

        if(cursor.moveToNext()) {
            quantity = cursor.getString(cursor.getColumnIndex(PRODUCT_QUANTITY)).toInt()
        }

        return quantity
    }

    fun calculateOrder():OrderSummary {
        var db = DBHelper()
        var totalPrice = 0.0
        var totalMrp = 0.0
        var totalDiscount = 0.0
        var checkDelivery: Boolean

        var totalQuantity = 0
        var list:ArrayList<ProductsDB> = ArrayList()

        list = db.readData()

        for(i in 0 until list.size) {
            totalPrice += (list[i].price.toDouble()*list[i].quantity.toDouble())
            totalMrp += (list[i].mrp *list[i].quantity.toDouble())
            totalQuantity += list[i].quantity
        }

        totalDiscount = totalMrp - totalPrice

        if(totalPrice > 300) {
            checkDelivery = true
        } else{ checkDelivery = false}

        return OrderSummary(totalPrice, totalMrp, totalDiscount,totalQuantity, checkDelivery)
    }

    fun getTotalQuantity():Int {
        var totalQuantity = 0
        var db = DBHelper()
        var list = db.readData()

        for(i in 0 until list.size) {
            totalQuantity += list[i].quantity
        }
        return totalQuantity
    }

}