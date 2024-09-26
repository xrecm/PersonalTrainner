package com.example.personaltrainner.business

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.personaltrainner.data.RutinaRepository

class RutinaViewModelFactory(private val repository: RutinaRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RutinaViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RutinaViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}