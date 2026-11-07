package com.example.week2

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TodoAdapter(private val items: MutableList<Todo>
                 ,private val onToggle: (position: Int) -> Unit
                  ,private val onDelete: (position: Int) -> Unit)
    : RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {

    class TodoViewHolder(view: View) : RecyclerView.ViewHolder(view)
    {
        val todoText: TextView = view.findViewById(R.id.todoText_id)
        val deleteButton: Button = view.findViewById(R.id.deleteButton_id)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder
    {
        val row = LayoutInflater.from(parent.context)
            .inflate(R.layout.todobar_layout, parent, false)
        return TodoViewHolder(row)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int)
    {
        val todo = items[position]
        holder.todoText.text = todo.text

        if (todo.done)
        {
            holder.todoText.paintFlags =
                holder.todoText.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            holder.todoText.alpha = 0.4f
        }
        else
        {
            holder.todoText.paintFlags =
                holder.todoText.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            holder.todoText.alpha = 1f
        }

        holder.itemView.setOnClickListener {
            val currentPosition = holder.bindingAdapterPosition
            if (currentPosition != RecyclerView.NO_POSITION) {
                onToggle(currentPosition)
            }
        }
        holder.deleteButton.setOnClickListener {
            val currentPosition = holder.bindingAdapterPosition
            if (currentPosition != RecyclerView.NO_POSITION) {
                onDelete(currentPosition)
            }
        }
    }
}
