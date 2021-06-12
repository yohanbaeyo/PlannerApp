package com.yhcoding.testtodo

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.yhcoding.testtodo.databinding.ActivityAddBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.Instant
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
        val size = intent.getIntExtra("size",0)

        binding.startDateInput.setOnClickListener {
            val dpd=DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, mYear, mMonth, mDay ->
                    binding.startDateInput.text = "" + mYear + "-" + (mMonth + 1) + "-" + mDay
                }, year, month, day)
            dpd.show()
        }
        binding.endDateInput.setOnClickListener {
            val dpd=DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, mYear, mMonth, mDay ->
                binding.endDateInput.text = ""+mYear+"-"+(mMonth+1)+"-"+mDay
            }, year, month, day)
            dpd.show()
        }
        binding.saveBtn.setOnClickListener {
            if(TextUtils.isEmpty(binding.titleInput.text.toString())) {
                binding.titleInput.error = "내용을 입력하세요."
                val errorMessage = Toast.makeText(this, "할 일이 작성되지 않았습니다.", Toast.LENGTH_SHORT)
                errorMessage.show()

                return@setOnClickListener
            }
            val startDate = when(binding.startDateInput.text.toString().length) {
                0 -> Instant.now().epochSecond
                else -> SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(binding.startDateInput.text.toString()).time/1000
            }
            val endDate = when(binding.endDateInput.text.toString().length) {
                0 -> Instant.now().epochSecond
                else -> SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(binding.endDateInput.text.toString()).time / 1000
            }
            if(startDate>endDate) {
                val errorMessage = Toast.makeText(this, "끝 날짜가 시작 날짜보다 이릅니다.", Toast.LENGTH_SHORT)
                errorMessage.show()
                return@setOnClickListener
            }
            CoroutineScope(Dispatchers.IO).launch {
                val newToDoItem= ToDo_Item(
                    binding.titleInput.text.toString(), startDate, endDate, size
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