package com.example.janitri_color_notes_task.RoomDB

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ColorDao {
    @Insert
    suspend fun insertColor(color: ColorEntity)

    @Update
    suspend fun updateColor(color:ColorEntity)

    @Query("SELECT * FROM colors")
    suspend fun getAllColors(): List<ColorEntity>

}