package com.example.janitri_color_notes_task.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.janitri_color_notes_task.Firebase.FirebaseCallback
import com.example.janitri_color_notes_task.RoomDB.ColorEntity
import com.example.janitri_color_notes_task.Repository.ColorRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ColorViewModel(private val colorRepository: ColorRepository) : ViewModel() {
    var _colors = MutableLiveData<List<ColorEntity>>()
    val colors: LiveData<List<ColorEntity>> = _colors

    fun insertColor(color: ColorEntity) {
        viewModelScope.launch {
            colorRepository.insertColor(color)
        }
    }

    fun updateColor(color: ColorEntity) {
        viewModelScope.launch {
            colorRepository.updateColor(color)
        }
    }

    fun firebase_sync(callback: FirebaseCallback){
        viewModelScope.launch(Dispatchers.IO) {
            colorRepository.firebase_sync(_colors, callback)
        }
    }

    fun getAllColors(){
        viewModelScope.launch {
            val allColors = colorRepository.getAllColors()
            _colors.value = allColors
            Log.e("Colorrrs","${_colors.value}")

        }
    }
}