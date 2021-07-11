package com.yhcoding.plannerapp

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE

@Dao
interface GoalItemDao {
    @Query("SELECT * FROM goal_item ORDER BY 4")
    fun getAll() : List<GoalItem>

    @Insert(onConflict = REPLACE)
    fun insert(todoItem: GoalItem)

    @Query("DELETE from goal_item")
    fun deleteAll()

    @Delete
    fun delete(todoItem: GoalItem)

    @Update
    fun update(todoItem: GoalItem)

    @Update(entity = GoalItem::class)
    fun updateSeq(todoItem: ItemForSeqUpdate)

    @Entity
    data class ItemForSeqUpdate(
        @PrimaryKey val id:Long,
        @ColumnInfo val order:Int
        )
}