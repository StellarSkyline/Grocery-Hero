package com.example.groceryhero.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.example.groceryhero.R
import com.example.groceryhero.helper.SessionManager
import com.example.groceryhero.helper.setupToolbar
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.fragment_profile.view.*
import kotlinx.android.synthetic.main.fragment_profile.view.text_view_email
import kotlinx.android.synthetic.main.fragment_profile.view.text_view_name

class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        init()
    }

    private fun init() {
        this.setupToolbar("Profile")
        var session = SessionManager()

        var user = session.getUser()

        text_view_name.text = "Name: ${user.name}"
        text_view_email.text = "Email: ${user.email}"
        text_view_mobile.text = "Mobile: ${user.mobile}"

        button_logout.setOnClickListener{
            session.logout()
            startActivity(Intent(this, StartActivity::class.java))

        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> {finish()}
            R.id.menu_cart -> {startActivity(Intent(this,CartActivity::class.java))}
        }
        return true
    }
}
