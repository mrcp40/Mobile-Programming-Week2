package com.example.week2

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.core.view.updatePadding
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class HomeActivity : AppCompatActivity()
{

    private lateinit var prefs: SharedPreferences

    private lateinit var welcomeTextView: TextView
    private lateinit var emptyTextView: TextView
    private lateinit var newTodoEditText: EditText
    private lateinit var todoRecyclerView: RecyclerView


    private val todos = mutableListOf<Todo>()
    private lateinit var todoAdapter: TodoAdapter


    private var currentEmail: String? = null

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.home_layout)

        prefs = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

        welcomeTextView = findViewById(R.id.welcomeTextView_id)
        emptyTextView = findViewById(R.id.emptyTextView_id)
        newTodoEditText = findViewById(R.id.newTodoEditText_id)
        todoRecyclerView = findViewById(R.id.todoList_id)

        todoAdapter = TodoAdapter(
            items = todos,
            onToggle = { position -> toggleTodo(position) },
            onDelete = { position -> deleteTodo(position) }
        )
        todoRecyclerView.layoutManager = LinearLayoutManager(this)
        todoRecyclerView.adapter = todoAdapter

        findViewById<Button>(R.id.addButton_id).setOnClickListener { addTodo() }
        newTodoEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE)
            {
                addTodo()
                true
            } else
            {
                false
            }
        }

        val root = findViewById<View>(R.id.homeRoot_id)
        val basePadding = (16 * resources.displayMetrics.density).toInt()
        ViewCompat.setOnApplyWindowInsetsListener(root) { view, insets ->
            val bars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.updatePadding(top = basePadding + bars.top, bottom = basePadding + bars.bottom)
            insets
        }
    }

    override fun onResume()
    {
        super.onResume()
        refreshList()
    }


    private fun refreshList()
    {
        currentEmail = prefs.getString("email", null)

        todos.clear()
        currentEmail?.let { email ->
            todos.addAll(TodoStore.load(this, email))
        }
        todoAdapter.notifyDataSetChanged()

        emptyTextView.isVisible = todos.isEmpty()
        updateHeader()
    }

    private fun updateHeader() {
        val name = prefs.getString("name", null)
        welcomeTextView.text = if (currentEmail == null) {
            "Not logged in - tap Profile to log in."
        } else {
            "Hi ${name ?: currentEmail ?: "user"} - ${todos.size} item(s)"
        }
    }


    private fun addTodo()
    {
        val email = currentEmail
        if (email == null)
        {
            Toast.makeText(this, "Please log in first (tap Profile)", Toast.LENGTH_SHORT).show()
            return
        }

        val text = newTodoEditText.text.toString().trim()
        when {
            text.isEmpty() -> {
                newTodoEditText.error = "Item cannot be empty"
            }

            !isLegal(text) -> {
                newTodoEditText.error =
                    "Illegal characters. Use letters, numbers, spaces or . , ! ? ' - _"
            }

            todos.any { it.text.equals(text, ignoreCase = true) } -> {
                newTodoEditText.error = "This item is already in the list"
            }

            else -> {
                todos.add(0, Todo(text))
                todoAdapter.notifyItemInserted(0)
                todoRecyclerView.scrollToPosition(0)
                newTodoEditText.text.clear()
                emptyTextView.isVisible = false
                saveTodos(email)
                updateHeader()
            }
        }
    }

    private fun isLegal(text: String): Boolean
    {
        return text.all { char ->
            char.isLetterOrDigit() || char == ' ' || char in ".,!?-' _"
        }
    }

    private fun toggleTodo(position: Int)
    {
        if (position !in todos.indices) return

        todos[position].done = !todos[position].done
        todoAdapter.notifyItemChanged(position)
        currentEmail?.let { saveTodos(it) }
    }

    private fun deleteTodo(position: Int)
    {
        if (position !in todos.indices) return

        todos.removeAt(position)
        todoAdapter.notifyItemRemoved(position)
        emptyTextView.isVisible = todos.isEmpty()
        currentEmail?.let { saveTodos(it) }
        updateHeader()
    }

    private fun saveTodos(email: String)
    {
        TodoStore.save(this, email, todos)
    }


    fun loginFun(v: View)
    {
        val nextActivity = if (prefs.getBoolean("is_logged_in", false))
        {
            Intent(this, InfoActivitty::class.java)
        }
        else
        {
            Intent(this, LoginActivity::class.java)
        }
        startActivity(nextActivity)
    }
}
