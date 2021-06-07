package com.yhcoding.testtodo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "todo_item")
data class ToDo_Item(
    @ColumnInfo val title:String,
    @ColumnInfo val startDate: Long,
    @ColumnInfo val endDate:Long
    ) {
    @PrimaryKey(autoGenerate = true)var id: Long = 0
}