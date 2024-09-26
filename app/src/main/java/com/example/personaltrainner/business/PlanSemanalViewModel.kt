package com.example.personaltrainner.business

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.personaltrainner.data.PlanSemanalEntity
import com.example.personaltrainner.data.PlanSemanalRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class PlanSemanalViewModel(private val repository: PlanSemanalRepository) : ViewModel() {

    fun insertarPlanSemanal(planSemanal: PlanSemanalEntity) {
        viewModelScope.launch {
            repository.insertarPlanSemanal(planSemanal)
        }
    }

    fun obtenerTodosLosPlanes(clienteId: Int): Flow<List<PlanSemanalEntity>> {
        return repository.obtenerTodosLosPlanes(clienteId)
    }

    fun eliminarPlan(planId: Int) {
        viewModelScope.launch {
            repository.eliminarPlan(planId)
        }
    }
}
