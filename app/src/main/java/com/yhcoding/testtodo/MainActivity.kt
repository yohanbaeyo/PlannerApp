package com.yhcoding.testtodo

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.yhcoding.testtodo.databinding.ActivityMainBinding
import java.time.LocalDate

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val items = ArrayList<ToDo_Item>()
    var n:Int=4

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        binding = ActivityMainBinding.inflate(layoutInflater)
        for(i in 0 until n) {
            items.add(ToDo_Item("$i", LocalDate.now(), LocalDate.now()))
        }
        refreshRecyclerView()

        setContentView(binding.root)
        val actionBar: ActionBar? = supportActionBar
        actionBar?.hide()

        binding.floatingActionButton.setOnClickListener {
            items.add(ToDo_Item("$n", LocalDate.now(), LocalDate.now()))
            n++
            refreshRecyclerView()
        }
    }

    private fun refreshRecyclerView() {
        val adapter = ToDo_Item_Adapter(items)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
    }
}