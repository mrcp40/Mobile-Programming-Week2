package com.example.week2

import android.content.Context
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class InfoActivitty : AppCompatActivity() {

    lateinit var  nameInfoTextView: TextView
    lateinit var  emailInfoTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.info_layout)
        var pref=getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

        nameInfoTextView=findViewById(R.id.nameInfoTextView_id)
        emailInfoTextView=findViewById(R.id.emailInfoTextView_id)

        nameInfoTextView.text = pref.getString("name","")?:""
        emailInfoTextView.text=pref.getString("email","")?:""


    }
}