package com.yhcoding.testtodo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.yhcoding.testtodo.databinding.ActivityAddBinding
import com.yhcoding.testtodo.databinding.ActivityMainBinding

class AddActivity : AppCompatActivity() {
    private lateinit var binding:ActivityAddBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding = ActivityAddBinding.inflate(layoutInflater)
    }
}