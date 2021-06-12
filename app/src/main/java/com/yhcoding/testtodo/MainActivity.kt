package com.yhcoding.testtodo

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.yhcoding.testtodo.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDate


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var todoItems = ArrayList<ToDo_Item>()
    private var todoItemDb:ToDo_Item_DB? = null
    private lateinit var adapter:ToDo_Item_Adapter
    private lateinit var touchHelper:ItemTouchHelper

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        todoItemDb = ToDo_Item_DB.getInstance(this)
        adapter = ToDo_Item_Adapter(todoItems, todoItemDb)
        touchHelper = ItemTouchHelper(ItemTouchHelperCallback(adapter))

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
        touchHelper.attachToRecyclerView(binding.recyclerView)

        refreshRecyclerView()

        val actionBar: ActionBar? = supportActionBar
        actionBar?.hide()

        binding.floatingActionButton.setOnClickListener {
            val intent = Intent(this, AddActivity::class.java)
            intent.putExtra("size", todoItems.size)
            startActivity(intent)
        }
    }

    private fun refreshRecyclerView() {
        CoroutineScope(Dispatchers.IO).launch {
            todoItems.clear()
            todoItems.addAll(todoItemDb?.todo_Item_Dao()!!.getAll())
            adapter.notifyDataSetChanged()
        }
    }

    override fun onDestroy() {
        todoItemDb?.destroyInstance()
        todoItemDb = null
        super.onDestroy()
    }
}