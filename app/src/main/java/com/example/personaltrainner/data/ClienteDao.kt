package com.example.personaltrainner.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ClienteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarCliente(cliente: ClienteEntity)

    @Query("SELECT * FROM clientes WHERE id = :clienteId")
    fun obtenerClientePorId(clienteId: Int): Flow<ClienteEntity?>


    @Update
    suspend fun actualizarCliente(cliente: ClienteEntity)

    @Delete
    suspend fun eliminar(cliente: ClienteEntity)
    @Query("SELECT * FROM clientes WHERE id = :clienteId")
    suspend fun obtenerCliente(clienteId: Int): ClienteEntity

    @Query("SELECT * FROM clientes")
    fun obtenerTodosLosClientes(): Flow<List<ClienteEntity>>
}