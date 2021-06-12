package com.yhcoding.testtodo

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ToDo_Item::class], version = 2)
abstract class ToDo_Item_DB :RoomDatabase() {
    abstract fun todo_Item_Dao() : ToDo_Item_Dao

    companion object {
        private var INSTANCE : ToDo_Item_DB? = null

        @Synchronized
        fun getInstance(context: Context): ToDo_Item_DB? {
            if(INSTANCE==null) {
                synchronized(ToDo_Item_DB::class) {
                    INSTANCE= Room.databaseBuilder(context.applicationContext, ToDo_Item_DB::class.java, "todo_item.db")
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