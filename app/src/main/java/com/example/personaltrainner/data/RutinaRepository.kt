package com.example.personaltrainner.data

import kotlinx.coroutines.flow.Flow

class RutinaRepository(private val RutinaDao: RutinaDao) {

    suspend fun insertarRutina(rutina: RutinaEntity) {
        RutinaDao.insertarRutina(rutina)
    }
    fun obtenerTodosLosPlanes(clienteId: Int) = RutinaDao.obtenerTodosLosPlanes(clienteId)
    suspend fun actualizarRutina(rutina: RutinaEntity) {
        RutinaDao.actualizarRutina(rutina)
    }

    suspend fun eliminarRutina(rutina: RutinaEntity) {
        RutinaDao.eliminarRutina(rutina)
    }
    fun obtenerRutinasPorCliente(clienteId: Int): Flow<List<RutinaEntity>> {
        return RutinaDao.obtenerRutinasPorCliente(clienteId)
    }
    fun obtenerRutinaPorId(rutinaId: Int): Flow<RutinaEntity?> {
        return RutinaDao.obtenerRutinaPorId(rutinaId)
    }
    fun obtenerRutinasRecientes(): Flow<List<RutinaEntity>> {
        return RutinaDao.obtenerRutinasRecientes()
    }
}
