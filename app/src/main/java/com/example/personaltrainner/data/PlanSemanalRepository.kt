package com.example.personaltrainner.data

class PlanSemanalRepository(private val planSemanalDao: PlanSemanalDao) {

    suspend fun insertarPlanSemanal(planSemanal: PlanSemanalEntity) {
        planSemanalDao.insertarPlanSemanal(planSemanal)
    }

    fun obtenerTodosLosPlanes(clienteId: Int) = planSemanalDao.obtenerTodosLosPlanes(clienteId)

    suspend fun eliminarPlan(planSemanalId: Int) {
        planSemanalDao.eliminarPlan(planSemanalId)
    }
}
