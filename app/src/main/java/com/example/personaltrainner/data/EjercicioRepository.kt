package com.example.personaltrainner.data

import kotlinx.coroutines.flow.Flow

class EjercicioRepository(private val ejercicioDao: EjercicioDao) {

    suspend fun insertarEjercicio(ejercicio: EjercicioEntity) {
        ejercicioDao.insertarEjercicio(ejercicio)
    }
    fun obtenerTodosLosEjercicios(): Flow<List<EjercicioEntity>> {
        return ejercicioDao.obtenerTodosLosEjercicios()
    }
    fun obtenerEjercicioPorId(ejercicioId: Int): Flow<EjercicioEntity?> {
        return ejercicioDao.obtenerEjercicioPorId(ejercicioId)
    }
    suspend fun actualizarEjercicio(ejercicio: EjercicioEntity) {
        ejercicioDao.actualizarEjercicio(ejercicio)
    }
    suspend fun eliminar(ejercicio: EjercicioEntity) {
        ejercicioDao.eliminar(ejercicio)
    }
}