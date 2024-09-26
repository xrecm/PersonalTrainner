package com.example.personaltrainner.data

import kotlinx.coroutines.flow.Flow

class MembresiaRepository(private val membresiaDao: MembresiaDao) {

    suspend fun insertarMembresia(membresia: MembresiaEntity) {
        membresiaDao.insertarMembresia(membresia)
    }

    suspend fun eliminarMembresia(membresia: MembresiaEntity) {
        membresiaDao.eliminarMembresia(membresia)
    }

    suspend fun actualizarMembresia(membresia: MembresiaEntity) {
        membresiaDao.actualizarMembresia(membresia)
    }

    fun obtenerMembresiaPorId(id: Int): Flow<MembresiaEntity?> {
        return membresiaDao.obtenerMembresiaPorId(id)
    }

    fun obtenerTodasLasMembresias(): Flow<List<MembresiaEntity>> {
        return membresiaDao.obtenerTodasLasMembresias()
    }
}
