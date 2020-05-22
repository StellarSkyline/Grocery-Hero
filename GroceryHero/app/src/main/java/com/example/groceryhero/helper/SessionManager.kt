package com.example.groceryhero.helper

import android.content.Context
import com.example.groceryhero.app.Config
import com.example.groceryhero.app.MyActivity
import com.example.groceryhero.model.Users

class SessionManager() {

    //create shared preference
    var sharePreference = MyActivity.instance.getSharedPreferences(Config.FILE_NAME, Context.MODE_PRIVATE)
    //create editor
    var editor = sharePreference.edit()

    //Create Companion Object
    companion object{
        const val KEY_IS_LOGGED_IN = "isLoggedIn"
    }

    fun register(user:Users) {
        editor.putString(Users.KEY_NAME, user.name)
        editor.putString(Users.KEY_Mobile, user.mobile)
        editor.putString(Users.KEY_EMAIL, user.email)
        editor.putString(Users.KEY_PASSWORD, user.password)
        editor.putString(Users.KEY_USER_ID, user.id)
        editor.commit()
    }


    fun login() {
        editor.putBoolean(KEY_IS_LOGGED_IN, true)
        editor.commit()
    }



    fun getUser(): Users {
        var name = sharePreference.getString(Users.KEY_NAME, null)
        var email = sharePreference.getString(Users.KEY_EMAIL, null)
        var password = sharePreference.getString(Users.KEY_PASSWORD, null)
        var mobile = sharePreference.getString(Users.KEY_Mobile, null)
        var id = sharePreference.getString(Users.KEY_USER_ID, null)
        var user = Users(name = name!!, email = email!!, password = password!!, mobile = mobile!!, id = id!!)

        return user
    }

    fun logout() {
        editor.clear()
        editor.commit()
    }

    fun isLoggedIn():Boolean {
        return sharePreference.getBoolean(KEY_IS_LOGGED_IN, false)
    }

    fun saveToken(token:String) {
        editor.putString("token", token)
        editor.commit()
    }

    fun getId():String {
        return sharePreference.getString(Users.KEY_USER_ID, null)!!
    }

    fun getName():String {
        return sharePreference.getString(Users.KEY_NAME, null)!!
    }

    fun getMobile():String {
        return sharePreference.getString(Users.KEY_Mobile, null)!!
    }


}