package com.yhcoding.testtodo

import android.os.Build
import android.system.Os.bind
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.yhcoding.testtodo.databinding.TodoItemBinding
import java.time.Instant
import java.time.ZoneId

class ToDo_Item_Adapter(private val items:ArrayList<ToDo_Item>) : RecyclerView.Adapter<ToDo_Item_Adapter.ToDo_Item_ViewHolder>(){
    class ToDo_Item_ViewHolder(val binding: TodoItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun getItemCount(): Int = items.size

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ToDo_Item_ViewHolder, position: Int) {
        val item = items[position]
        val listener = View.OnClickListener { it ->
            Toast.makeText(it.context, "Clicked" + item.title, Toast.LENGTH_SHORT).show()
        }
        holder.binding.titleText.text=items[position].title
        holder.binding.startDate.text= Instant.ofEpochSecond(items[position].startDate).atZone(ZoneId.of("UTC")).toLocalDate().toString()
        holder.binding.endDate.text=Instant.ofEpochSecond(items[position].endDate).atZone(ZoneId.of("UTC")).toLocalDate().toString()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDo_Item_ViewHolder{
        val binding = TodoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ToDo_Item_ViewHolder(binding)
    }
}