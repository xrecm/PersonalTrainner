package com.example.personaltrainner.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface RutinaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarRutina(rutina: RutinaEntity)

    @Query("SELECT * FROM rutinas WHERE clienteId = :clienteId")
    fun obtenerTodosLosPlanes(clienteId: Int): Flow<List<RutinaEntity>>

    @Query("SELECT * FROM rutinas WHERE clienteId = :clienteId ORDER BY fecha ASC")
    fun obtenerRutinasPorCliente(clienteId: Int): Flow<List<RutinaEntity>>
    @Update
    suspend fun actualizarRutina(rutina: RutinaEntity)
    @Delete
    suspend fun eliminarRutina(rutina: RutinaEntity)
    @Query("SELECT * FROM rutinas WHERE id = :rutinaId LIMIT 1")
    fun obtenerRutinaPorId(rutinaId: Int): Flow<RutinaEntity?>
    // Nueva función para obtener rutinas recientes
    @Query("SELECT * FROM rutinas ORDER BY fecha DESC LIMIT 10") // Cambia el número de límites si quieres más o menos
    fun obtenerRutinasRecientes(): Flow<List<RutinaEntity>>
}
