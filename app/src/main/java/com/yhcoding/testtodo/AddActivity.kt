package com.yhcoding.testtodo

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import com.yhcoding.testtodo.databinding.ActivityAddBinding
import com.yhcoding.testtodo.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class AddActivity : AppCompatActivity() {
    private lateinit var binding:ActivityAddBinding
    private var todoItemDb:ToDo_Item_DB? = null

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        todoItemDb=ToDo_Item_DB.getInstance(this)

        binding.saveBtn.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                val newToDoItem: ToDo_Item= ToDo_Item(
                    binding.titleInput.text.toString(),
                    SimpleDateFormat("yyyy-MM-dd").parse(binding.startDateInput.text.toString()+binding).time/1000,
                    SimpleDateFormat("yyyy-MM-dd").parse(binding.endDateInput.text.toString()+binding).time/1000
                )
                todoItemDb?.todo_Item_Dao()?.insert(newToDoItem)
            }
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

    }

    override fun onDestroy() {
        todoItemDb?.destroyInstance()
        super.onDestroy()
    }
}