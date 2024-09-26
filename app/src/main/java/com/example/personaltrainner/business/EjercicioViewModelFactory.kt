package com.example.personaltrainner.business

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.personaltrainner.data.EjercicioRepository

class EjercicioViewModelFactory(private val repository: EjercicioRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EjercicioViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return EjercicioViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
