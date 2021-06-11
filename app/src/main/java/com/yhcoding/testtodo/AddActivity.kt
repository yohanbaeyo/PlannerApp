package com.yhcoding.testtodo

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.yhcoding.testtodo.databinding.ActivityAddBinding
import com.yhcoding.testtodo.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class AddActivity : AppCompatActivity() {
    private lateinit var binding:ActivityAddBinding
    private var todoItemDb:ToDo_Item_DB? = null

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        todoItemDb=ToDo_Item_DB.getInstance(this)

        val c=Calendar.getInstance()
        val year=c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        binding.startDateInput.setOnClickListener {
            val dpd=DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, mYear, mMonth, mDay ->
                binding.startDateInput.setText(""+mYear+"-"+(mMonth+1)+"-"+mDay)
            }, year, month, day)
            dpd.show()
        }
        binding.endDateInput.setOnClickListener {
            val dpd=DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, mYear, mMonth, mDay ->
                binding.endDateInput.setText(""+mYear+"-"+(mMonth+1)+"-"+mDay)
            }, year, month, day)
            dpd.show()
        }
        binding.saveBtn.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                val newToDoItem: ToDo_Item= ToDo_Item(
                    binding.titleInput.text.toString(),
                    SimpleDateFormat("yyyy-MM-dd").parse(binding.startDateInput.text.toString()).time/1000,
                    SimpleDateFormat("yyyy-MM-dd").parse(binding.endDateInput.text.toString()).time/1000,
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