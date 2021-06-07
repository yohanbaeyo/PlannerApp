package com.yhcoding.testtodo

import android.system.Os.bind
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.yhcoding.testtodo.databinding.TodoItemBinding

class ToDo_Item_Adapter(private val items:ArrayList<ToDo_Item>) : RecyclerView.Adapter<ToDo_Item_Adapter.ToDo_Item_ViewHolder>(){
    class ToDo_Item_ViewHolder(val binding: TodoItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ToDo_Item_ViewHolder, position: Int) {
        val item = items[position]
        val listener = View.OnClickListener { it ->
            Toast.makeText(it.context, "Clicked" + item.title, Toast.LENGTH_SHORT).show()
        }
        holder.binding.todoItem =items[position]
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDo_Item_ViewHolder{
        val binding = TodoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ToDo_Item_ViewHolder(binding)
    }
}