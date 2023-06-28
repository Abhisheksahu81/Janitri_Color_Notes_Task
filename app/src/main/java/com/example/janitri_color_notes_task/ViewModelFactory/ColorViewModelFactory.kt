package com.example.janitri_color_notes_task.ViewModelFactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.janitri_color_notes_task.Repository.ColorRepository
import com.example.janitri_color_notes_task.ViewModel.ColorViewModel

class ColorViewModelFactory(private val colorRepository: ColorRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ColorViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ColorViewModel(colorRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}