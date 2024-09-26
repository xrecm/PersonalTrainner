package com.example.personaltrainner.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ClienteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarCliente(cliente: ClienteEntity)

    @Update
    suspend fun actualizarCliente(cliente: ClienteEntity)

    @Delete
    suspend fun eliminarCliente(cliente: ClienteEntity)

    @Query("SELECT * FROM clientes")
    fun obtenerTodosLosClientes(): Flow<List<ClienteEntity>>
}