package com.yhcoding.plannerapp

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "goal_item")
data class GoalItem @RequiresApi(Build.VERSION_CODES.O) constructor(
    @ColumnInfo val title:String,
    @ColumnInfo val startDate: Long,
    @ColumnInfo val endDate:Long,
    @ColumnInfo val order:Int,
    @PrimaryKey(autoGenerate = true)var id: Long = 0
    )
