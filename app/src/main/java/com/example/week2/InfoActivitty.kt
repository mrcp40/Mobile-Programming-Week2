package com.example.week2

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding

class InfoActivitty : AppCompatActivity()
{

    lateinit var nameInfoTextView: TextView
    lateinit var emailInfoTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.info_layout)

        val prefs = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

        nameInfoTextView = findViewById(R.id.nameInfoTextView_id)
        emailInfoTextView = findViewById(R.id.emailInfoTextView_id)

        nameInfoTextView.text = prefs.getString("name", "") ?: ""
        emailInfoTextView.text = prefs.getString("email", "") ?: ""

        val root = findViewById<View>(R.id.infoRoot_id)
        val basePadding = (24 * resources.displayMetrics.density).toInt()
        ViewCompat.setOnApplyWindowInsetsListener(root) { view, insets ->
            val bars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.updatePadding(top = basePadding + bars.top, bottom = basePadding + bars.bottom)
            insets
        }
    }

    fun logout(v: View)
    {
        getSharedPreferences("app_prefs", Context.MODE_PRIVATE).edit {
            putBoolean("is_logged_in", false)
            remove("email")
            remove("name")
        }
        finish()
    }
}
