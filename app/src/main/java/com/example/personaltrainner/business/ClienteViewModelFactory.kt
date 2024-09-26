package com.example.personaltrainner.business

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.personaltrainner.data.ClienteRepository

class ClienteViewModelFactory(private val repository: ClienteRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ClienteViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ClienteViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
