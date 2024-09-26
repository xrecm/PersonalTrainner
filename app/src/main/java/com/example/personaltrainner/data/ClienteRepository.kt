package com.example.personaltrainner.data

import kotlinx.coroutines.flow.Flow

class ClienteRepository(private val clienteDao: ClienteDao) {

    suspend fun insertarCliente(cliente: ClienteEntity) {
        clienteDao.insertarCliente(cliente)
    }

    suspend fun actualizarCliente(cliente: ClienteEntity) {
        clienteDao.actualizarCliente(cliente)
    }

    suspend fun eliminarCliente(cliente: ClienteEntity) {
        clienteDao.eliminarCliente(cliente)
    }

    fun obtenerTodosLosClientes(): Flow<List<ClienteEntity>> {
        return clienteDao.obtenerTodosLosClientes()
    }
}