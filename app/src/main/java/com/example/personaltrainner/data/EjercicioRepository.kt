package com.example.personaltrainner.data

import kotlinx.coroutines.flow.Flow

class EjercicioRepository(private val ejercicioDao: EjercicioDao) {

    suspend fun insertarEjercicio(ejercicio: EjercicioEntity) {
        ejercicioDao.insertarEjercicio(ejercicio)
    }

    fun obtenerTodosLosEjercicios(): Flow<List<EjercicioEntity>> {
        return ejercicioDao.obtenerTodosLosEjercicios()
    }
}