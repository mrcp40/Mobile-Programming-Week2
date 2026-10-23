package com.example.week2

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit

class HomeActivity : AppCompatActivity()
{
    lateinit var myTextView: TextView
    lateinit var loginButton: Button

    lateinit var prefs: SharedPreferences
    var is_login_in=false

    val loginLuncher= registerForActivityResult(ActivityResultContracts.StartActivityForResult())
    {
        result -> if(result.resultCode==RESULT_OK)
                {
                    is_login_in = prefs.getBoolean("is_logged_in", false)
                }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.home_layout)

        prefs = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        is_login_in = prefs.getBoolean("is_logged_in", false)

        prefs.edit { putBoolean("is_logged_in", false) }
        prefs.edit { putString("email",null) }
        prefs.edit { putString("name",null) }

        myTextView=findViewById(R.id.myTextView_id)
        loginButton=findViewById(R.id.loginButton_id)
    }


    // void loginFun(View v)
    public fun loginFun(v: View)
    {
        val intentLogin =Intent(this,    LoginActivity::class.java)
        val intentInfo= Intent(this, InfoActivitty::class.java)

        if(is_login_in)
        {
            startActivity(intentInfo)
        }
        else
        {
            loginLuncher.launch(intentLogin)
        }

    }
}