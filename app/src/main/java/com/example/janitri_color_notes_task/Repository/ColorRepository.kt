package com.example.janitri_color_notes_task.Repository

import FirebaseUtility
import androidx.lifecycle.MutableLiveData
import com.example.janitri_color_notes_task.Firebase.FirebaseCallback
import com.example.janitri_color_notes_task.RoomDB.ColorDao
import com.example.janitri_color_notes_task.RoomDB.ColorEntity

class ColorRepository(private val colorDao: ColorDao) {
    suspend fun insertColor(color: ColorEntity) {
        colorDao.insertColor(color)
    }

    suspend fun updateColor(color: ColorEntity) {
        colorDao.updateColor(color)
    }

    suspend fun getAllColors(): List<ColorEntity> {
        return colorDao.getAllColors()
    }



    suspend fun firebase_sync(list: MutableLiveData<List<ColorEntity>>, callback: FirebaseCallback)
    {
         val firebase = FirebaseUtility();

        firebase.setFirbasecallback(callback)
        firebase.Update_database(list)

    }

}