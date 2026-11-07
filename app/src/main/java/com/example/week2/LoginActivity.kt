package com.example.week2

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit

class LoginActivity : AppCompatActivity()
{

    lateinit var nameEditText: EditText
    lateinit var emailEditText: EditText

    val users: Array<User> = arrayOf(
        User("123@gmail.com"),
        User("456@gmail.com"),
        User("789@gmail.com")
    )

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.login_layout)

        nameEditText = findViewById(R.id.loginTextView_id)
        emailEditText = findViewById(R.id.emailTextView_id)
    }

    public fun goBack(v: View)
    {
        val email = emailEditText.text.toString().trim()
        val name = nameEditText.text.toString().trim()
        val ok = login(User(email))

        if (ok)
        {
            getSharedPreferences("app_prefs", Context.MODE_PRIVATE).edit {
                putBoolean("is_logged_in", true)
                putString("email", email)
                putString("name", name.ifEmpty { email })
            }
            Toast.makeText(this, "Welcome, ${name.ifEmpty { email }}", Toast.LENGTH_SHORT).show()
            setResult(RESULT_OK)
        }
        else
        {
            Toast.makeText(this, "No user found for that email", Toast.LENGTH_SHORT).show()
            setResult(RESULT_CANCELED)
        }

        finish()
    }

    fun login(user: User): Boolean
    {
        var found = false
        for (u: User in users)
        {
            if (user.email == u.email)
            {
                found = true
            }
        }
        return found
    }
}

class User(val email: String)
