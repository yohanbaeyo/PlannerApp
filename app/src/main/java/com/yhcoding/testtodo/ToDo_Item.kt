package com.yhcoding.testtodo

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todo_item")
data class ToDo_Item @RequiresApi(Build.VERSION_CODES.O) constructor(
    @ColumnInfo val title:String,
    @ColumnInfo val startDate: Long,
    @ColumnInfo val endDate:Long,
    @ColumnInfo val order:Int,
    @PrimaryKey(autoGenerate = true)var id: Long = 0
    )
