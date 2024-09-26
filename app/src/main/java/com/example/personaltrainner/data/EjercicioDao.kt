package com.example.personaltrainner.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface EjercicioDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarEjercicio(ejercicio: EjercicioEntity)

    @Query("SELECT * FROM ejercicios")
    fun obtenerTodosLosEjercicios(): Flow<List<EjercicioEntity>>
    @Query("SELECT * FROM ejercicios WHERE id = :ejercicioId")
    fun obtenerEjercicioPorId(ejercicioId: Int): Flow<EjercicioEntity>
    @Update
    suspend fun actualizarEjercicio(ejercicio: EjercicioEntity)
    @Delete
    suspend fun eliminar(ejercicio: EjercicioEntity)
}


