package com.example.janitri_color_notes_task.RoomDB

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ColorEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun colorDao(): ColorDao
}