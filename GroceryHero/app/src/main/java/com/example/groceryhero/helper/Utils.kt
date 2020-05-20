package com.example.groceryhero.helper

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView
import kotlinx.android.synthetic.main.toolbar.*

fun AppCompatActivity.setupToolbar(title:String) {
    var myToolbar: Toolbar = this.toolbar
    myToolbar.title = title
    this.setSupportActionBar(myToolbar)

    //Set a back button on toolbar
    this.supportActionBar?.setDisplayHomeAsUpEnabled(true)

}

fun Context.toast(message:String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

}

fun Context.log(message:String) {
    Log.d("STLog", message)
}

fun RelativeLayout.show() {
    this.visibility = View.VISIBLE
}

fun RelativeLayout.hide() {
    this.visibility = View.GONE
}

fun ProgressBar.show() {
    this.visibility = View.VISIBLE
}

fun ProgressBar.hide() {
    this.visibility = View.GONE
}

fun LinearLayout.show() {
    this.visibility = View.VISIBLE
}

fun LinearLayout.hide() {
    this.visibility = View.GONE
}

fun CardView.show() {
    this.visibility = View.VISIBLE
}

fun CardView.hide() {
    this.visibility = View.GONE
}