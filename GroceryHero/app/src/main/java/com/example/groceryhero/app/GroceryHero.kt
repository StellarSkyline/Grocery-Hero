package com.example.groceryhero.app

import android.app.Application

class MyActivity: Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object{
        lateinit var instance:MyActivity
            private set
    }
}