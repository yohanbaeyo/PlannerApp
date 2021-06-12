package com.yhcoding.testtodo

import android.content.ClipData
import android.content.Context
import android.os.Build
import android.system.Os.bind
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.yhcoding.testtodo.databinding.TodoItemBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.ZoneId

class ToDo_Item_Adapter(private val items:ArrayList<ToDo_Item>, private var db: ToDo_Item_DB?) : RecyclerView.Adapter<ToDo_Item_Adapter.ToDo_Item_ViewHolder>(), ItemTouchHelperListener{
    class ToDo_Item_ViewHolder(val binding: TodoItemBinding) : RecyclerView.ViewHolder(binding.root)
    private var todoItemDb:ToDo_Item_DB? = db

    override fun getItemCount(): Int = items.size

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ToDo_Item_ViewHolder, position: Int) {
        val item = items[position]
        val listener = View.OnClickListener { it ->
            Toast.makeText(it.context, "Clicked" + item.title, Toast.LENGTH_SHORT).show()
        }
        holder.binding.titleText.text=items[position].title
        holder.binding.startDate.text= Instant.ofEpochSecond(items[position].startDate).atZone(ZoneId.of("Asia/Seoul")).toLocalDate().toString()
        holder.binding.endDate.text=Instant.ofEpochSecond(items[position].endDate).atZone(ZoneId.of("Asia/Seoul")).toLocalDate().toString()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDo_Item_ViewHolder{
        val binding = TodoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ToDo_Item_ViewHolder(binding)
    }

    override fun onItemMove(fromPosition:Int, toPosition: Int):Boolean {
        var todoItem:ToDo_Item = items.get(fromPosition)
        items.removeAt(fromPosition)
        items.add(toPosition, todoItem)
        notifyItemMoved(fromPosition, toPosition)
        return true
    }

    fun updateDb() {
        var tmp:ToDo_Item_Dao.ItemForSeqUpdate
        CoroutineScope(Dispatchers.IO).launch {
            for((i,value) in items.withIndex()) {
                tmp= ToDo_Item_Dao.ItemForSeqUpdate(value.id, i)
//                tmp=value.copy(order = i)
                todoItemDb?.todo_Item_Dao()!!.updateSeq(tmp)
            }
        }
    }

    override fun onItemMoved(fromPosition: Int, toPosition: Int) {
        updateDb()
    }

    override fun onItemSwipe(position: Int) {
        val tmp=items[position]
        val job=CoroutineScope(Dispatchers.IO).launch{
            todoItemDb?.todo_Item_Dao()!!.delete(tmp)
            updateDb()
        }
        items.removeAt(position)
        notifyItemRemoved(position)
    }
}