package com.yhcoding.plannerapp

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.yhcoding.plannerapp.databinding.GoalItemBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.ZoneId
import kotlin.collections.ArrayList

class GoalItemAdapter(private val items:ArrayList<GoalItem>, db: GoalItemDB?) : RecyclerView.Adapter<GoalItemAdapter.ToDo_Item_ViewHolder>(), ItemTouchHelperListener{
    class ToDo_Item_ViewHolder(val binding: GoalItemBinding) : RecyclerView.ViewHolder(binding.root)
    private val todoItemDb:GoalItemDB? = db

    override fun getItemCount(): Int = items.size

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ToDo_Item_ViewHolder, position: Int) {
        holder.binding.titleText.text=items[position].title
        holder.binding.startDate.text= Instant.ofEpochSecond(items[position].startDate).atZone(ZoneId.systemDefault()).toLocalDate().toString()
        holder.binding.endDate.text=Instant.ofEpochSecond(items[position].endDate).atZone(ZoneId.systemDefault()).toLocalDate().toString()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDo_Item_ViewHolder{
        val binding = GoalItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ToDo_Item_ViewHolder(binding)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onItemMove(fromPosition:Int, toPosition: Int):Boolean {
        val todoItem:GoalItem = items.get(fromPosition)
        if(fromPosition>toPosition) {
            for(i in toPosition until fromPosition) {
                items[i+1]=items[i]
            }
        } else {
            for(i in fromPosition until toPosition) {
                items[i]=items[i+1]
            }
        }
        items[toPosition]=todoItem
        notifyItemMoved(fromPosition, toPosition)
        return true
    }

    fun updateDb() {
        var tmp:GoalItemDao.ItemForSeqUpdate
        CoroutineScope(Dispatchers.IO).launch {
            val it=items.iterator()
            var index=0
            while(it.hasNext()) {
                val item = it.next()
                todoItemDb?.todo_Item_Dao()!!.updateSeq(GoalItemDao.ItemForSeqUpdate(item.id, index))
                index++
            }
        }
    }

    override fun onItemMoved(fromPosition: Int, toPosition: Int) {
        updateDb()
    }

    override fun onItemSwipe(position: Int) {
        val tmp=items[position]
        CoroutineScope(Dispatchers.IO).launch{
            todoItemDb?.todo_Item_Dao()!!.delete(tmp)
            updateDb()
        }
        items.removeAt(position)
        notifyItemRemoved(position)
    }
}