package com.example.personaltrainner.business

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.personaltrainner.data.MembresiaRepository

class MembresiaViewModelFactory(
    private val repository: MembresiaRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MembresiaViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MembresiaViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
