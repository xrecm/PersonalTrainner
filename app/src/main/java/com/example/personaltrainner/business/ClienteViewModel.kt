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
    fun obtenerTodosLosClientes(): Flow<List<ClienteEntity>> {
        return repository.obtenerTodosLosClientes()
    }

    fun eliminarCliente(cliente: ClienteEntity) {
        viewModelScope.launch {
            repository.eliminarCliente(cliente)
        }
    }
    fun obtenerClientePorId(clienteId: Int): Flow<ClienteEntity?> {
        return repository.obtenerClientePorId(clienteId)
    }
    fun actualizarCliente(cliente: ClienteEntity) {
        viewModelScope.launch {
            repository.actualizarCliente(cliente)
        }
    }
}