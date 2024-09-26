package com.example.personaltrainner.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface EjercicioDao {

    // Inserción de un nuevo ejercicio, reemplazando si hay conflicto
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarEjercicio(ejercicio: EjercicioEntity)

    // Obtener todos los ejercicios
    @Query("SELECT * FROM ejercicios")
    fun obtenerTodosLosEjercicios(): Flow<List<EjercicioEntity>>

    // Puedes agregar más funciones como eliminar o actualizar si lo necesitas
}
