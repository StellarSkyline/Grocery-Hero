package com.example.groceryhero.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.groceryhero.R
import com.example.groceryhero.helper.SessionManager

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        init()
    }

    private fun init() {
        var handler = Handler()

        handler.postDelayed({
            checkUser()
        }, 3000)
    }

    private fun checkUser() {
        var mySession = SessionManager()

        if(mySession.isLoggedIn()) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        } else {startActivity(Intent(this, StartActivity::class.java))}
    }
}
