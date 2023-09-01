package com.example.todolist

import android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TodoAdapter (
    private val todos:MutableList<Todo>
) : RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {
    class TodoViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        return TodoViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_todo,
                parent,
                false
            )
        )
    }

    fun addTodo (todo: Todo) {
        todos.add(todo)
        notifyItemInserted(todos.size - 1)
    }

    fun deleteDoneTodos () {
        todos.removeAll {todo ->
            todo.isChecked
        }
        notifyDataSetChanged()
    }

    private fun toggleStrikeThrough (tvTodoList: TextView, isChecked: Boolean) {
        if(isChecked) {
            tvTodoList.paintFlags = tvTodoList.paintFlags or STRIKE_THRU_TEXT_FLAG
        }
        else {
            tvTodoList.paintFlags = tvTodoList.paintFlags and STRIKE_THRU_TEXT_FLAG.inv()
        }
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val curtodo = todos[position]
        holder.itemView.apply {
            val tvTodoList = findViewById<TextView>(R.id.tvTodoList) // Replace with the actual ID
            val cbDone = findViewById<CheckBox>(R.id.cbDone) // Replace with the actual ID

            tvTodoList.text = curtodo.title
            cbDone.isChecked = curtodo.isChecked

            toggleStrikeThrough(tvTodoList, cbDone.isChecked)
            cbDone.setOnCheckedChangeListener { _, isChecked ->
                toggleStrikeThrough(tvTodoList, isChecked)
                curtodo.isChecked = !curtodo.isChecked
            }
        }
    }

    override fun getItemCount(): Int {
        return todos.size
    }
}