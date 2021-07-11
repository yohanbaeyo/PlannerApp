package com.yhcoding.plannerapp

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [GoalItem::class], version = 3)
abstract class GoalItemDB :RoomDatabase() {
    abstract fun todo_Item_Dao() : GoalItemDao

    companion object {
        private var INSTANCE : GoalItemDB? = null

        @Synchronized
        fun getInstance(context: Context): GoalItemDB? {
            if(INSTANCE==null) {
                synchronized(GoalItemDB::class) {
                    INSTANCE= Room.databaseBuilder(context.applicationContext, GoalItemDB::class.java, "todo_item.db")
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE
        }
    }
    fun destroyInstance() {
        INSTANCE = null
    }
}