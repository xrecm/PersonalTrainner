package com.example.personaltrainner.data

class RutinaRepository(private val RutinaDao: RutinaDao) {

    suspend fun insertarRutina(rutina: RutinaEntity) {
        RutinaDao.insertarRutina(rutina)
    }

    fun obtenerTodosLosPlanes(clienteId: Int) = RutinaDao.obtenerTodosLosPlanes(clienteId)

    suspend fun eliminarPlan(rutinaId: Int) {
        RutinaDao.eliminarRutina(rutinaId)
    }
}
