package com.example.personaltrainner.data

import kotlinx.coroutines.flow.Flow

class ClienteRepository(private val clienteDao: ClienteDao) {

    suspend fun insertarCliente(cliente: ClienteEntity) {
        clienteDao.insertarCliente(cliente)
    }
    suspend fun eliminarCliente(cliente: ClienteEntity) {
        clienteDao.eliminar(cliente)
    }
    fun obtenerTodosLosClientes(): Flow<List<ClienteEntity>> {
        return clienteDao.obtenerTodosLosClientes()
    }
    fun obtenerClientePorId(clienteId: Int): Flow<ClienteEntity?> {
        return clienteDao.obtenerClientePorId(clienteId)
    }
    suspend fun actualizarCliente(cliente: ClienteEntity) {
        clienteDao.actualizarCliente(cliente)
    }
}