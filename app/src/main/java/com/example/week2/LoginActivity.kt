package com.example.week2

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.content.edit

class LoginActivity : AppCompatActivity() {

    lateinit var  goBackButton: Button
    lateinit var  loginTextView: TextView
    lateinit var  passwordTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.login_layout)
        loginTextView=findViewById(R.id.loginTextView_id)
        passwordTextView =findViewById(R.id.passwordTextView_id)

        loginTextView.text=intent.getStringExtra("myKey")
    }

    public fun goBack(v: View)
    {
        getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
            .edit { putBoolean("is_logged_in", true)
                    putString("name",loginTextView.text.toString())
                    putString("email",passwordTextView.text.toString())}


        setResult(RESULT_OK)
        finish()
    }
}