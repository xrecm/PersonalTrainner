package com.example.personaltrainner.business

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.personaltrainner.data.RutinaEntity
import com.example.personaltrainner.data.RutinaRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class RutinaViewModel(private val repository: RutinaRepository) : ViewModel() {

    fun insertarRutina(rutina: RutinaEntity) {
        viewModelScope.launch {
            repository.insertarRutina(rutina)
        }
    }
    fun obtenerTodosLosPlanes(clienteId: Int): Flow<List<RutinaEntity>> {
        return repository.obtenerTodosLosPlanes(clienteId)
    }
    fun actualizarRutina(rutina: RutinaEntity) {
        viewModelScope.launch {
            repository.actualizarRutina(rutina)
        }
    }
    fun eliminarRutina(rutina: RutinaEntity) {
        viewModelScope.launch {
            repository.eliminarRutina(rutina)
        }
    }
    fun obtenerRutinasPorCliente(clienteId: Int): Flow<List<RutinaEntity>> {
        return repository.obtenerRutinasPorCliente(clienteId)
    }
    fun obtenerRutinaPorId(rutinaId: Int): Flow<RutinaEntity?> {
        return repository.obtenerRutinaPorId(rutinaId)
    }
}
