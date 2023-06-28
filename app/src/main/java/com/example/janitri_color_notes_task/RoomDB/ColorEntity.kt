package com.example.janitri_color_notes_task.RoomDB

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "colors")
data class ColorEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "color_value")
    val colorValue: String,

    @ColumnInfo(name = "is_synced")
    var isSynced : String,

    @ColumnInfo(name = "created_at")
    val createdAt : String
)