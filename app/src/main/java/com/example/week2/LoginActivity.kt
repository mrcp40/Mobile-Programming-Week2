package com.example.week2

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit

class LoginActivity : AppCompatActivity() {

    lateinit var  goBackButton: Button
    lateinit var  loginTextView: TextView
    lateinit var  emailTextView: TextView

    val users: Array<User> = arrayOf(User("123@gmail.com"),
                                     User("456@gmail.com"),
                                     User("789@gmail.com"))


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.login_layout)
        loginTextView=findViewById(R.id.loginTextView_id)
        emailTextView =findViewById(R.id.emailTextView_id)

        loginTextView.text=intent.getStringExtra("myKey")
    }

    public fun goBack(v: View)
    {
        val email= emailTextView
        val name= loginTextView
        val ok=login(User(email.text.toString()))

        getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
            .edit { putBoolean("is_logged_in", ok)
                    putString("name",loginTextView.text.toString())
                    putString("email",emailTextView.text.toString())}

        if (ok) {
            Toast.makeText(this, "login", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "no user found", Toast.LENGTH_SHORT).show()
        }

        setResult(RESULT_OK)
        finish()
    }

    fun login(user: User) : Boolean
    {
        var found=false
        for (u: User in users)
        {
            if(user.email==u.email)
            {
                found=true
            }
        }
        return found
    }
}

class User(val email: String)