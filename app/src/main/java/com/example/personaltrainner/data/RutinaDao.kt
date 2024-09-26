package com.example.personaltrainner.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface RutinaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarRutina(rutina: RutinaEntity)

    @Query("SELECT * FROM rutinas WHERE clienteId = :clienteId")
    fun obtenerTodosLosPlanes(clienteId: Int): Flow<List<RutinaEntity>>

    @Query("SELECT * FROM rutinas WHERE clienteId = :clienteId ORDER BY fecha ASC")
    fun obtenerRutinasPorCliente(clienteId: Int): Flow<List<RutinaEntity>>

    @Query("DELETE FROM rutinas WHERE id = :planId")
    suspend fun eliminarRutina(planId: Int)
}
