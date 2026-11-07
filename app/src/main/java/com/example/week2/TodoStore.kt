package com.example.week2

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
object TodoStore {

    private const val PREFS_NAME = "app_prefs"
    private const val KEY_PREFIX = "todos_"

    private fun prefs(context: Context): SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    private fun keyFor(email: String): String = KEY_PREFIX + email

    fun load(context: Context, email: String): MutableList<Todo>
    {
        val raw = prefs(context).getString(keyFor(email), null) ?: return mutableListOf()

        val array = JSONArray(raw)
        return MutableList(array.length())
        { index -> val item = array.getJSONObject(index)
            Todo(
                text = item.getString("text"),
                done = item.optBoolean("done", false)
            )
        }
    }

    fun save(context: Context, email: String, todos: List<Todo>)
    {
        val array = JSONArray()
        for (todo in todos)
        {
            array.put(
                JSONObject()
                    .put("text", todo.text)
                    .put("done", todo.done)
            )
        }
        prefs(context).edit { putString(keyFor(email), array.toString()) }
    }
}
