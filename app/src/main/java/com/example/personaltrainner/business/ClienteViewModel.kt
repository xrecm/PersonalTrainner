package com.example.personaltrainner.business

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.personaltrainner.data.ClienteEntity
import com.example.personaltrainner.data.ClienteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class ClienteViewModel(private val repository: ClienteRepository) : ViewModel() {

    fun insertarCliente(cliente: ClienteEntity) {
        viewModelScope.launch {
            repository.insertarCliente(cliente)
        }
    }

    fun obtenerTodosLosClientes() = repository.obtenerTodosLosClientes()
}