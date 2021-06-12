package com.yhcoding.testtodo

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import java.time.LocalDate

@Dao
interface ToDo_Item_Dao {
    @Query("SELECT * FROM todo_item ORDER BY 4")
    fun getAll() : List<ToDo_Item>

    @Insert(onConflict = REPLACE)
    fun insert(todoItem: ToDo_Item)

    @Query("DELETE from todo_item")
    fun deleteAll()

    @Delete
    fun delete(todoItem: ToDo_Item)

    @Update
    fun update(todoItem: ToDo_Item)

    @Update(entity = ToDo_Item::class)
    fun updateSeq(todoItem: ItemForSeqUpdate)

    @Entity
    data class ItemForSeqUpdate(
        @PrimaryKey val id:Long,
        @ColumnInfo val order:Int
        )
}