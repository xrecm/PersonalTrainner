package com.example.personaltrainner.business

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.personaltrainner.data.MembresiaEntity
import com.example.personaltrainner.data.MembresiaRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.first

class MembresiaViewModel(private val repository: MembresiaRepository) : ViewModel() {
    val todasLasMembresias: Flow<List<MembresiaEntity>> = repository.obtenerTodasLasMembresias()

    fun insertarMembresia(membresia: MembresiaEntity) {
        viewModelScope.launch {
            repository.insertarMembresia(membresia)
        }
    }

    // Eliminar una membresía
    fun eliminarMembresia(membresia: MembresiaEntity) {
        viewModelScope.launch {
            repository.eliminarMembresia(membresia)
        }
    }

    // actualizar membresía
    fun actualizarMembresia(membresia: MembresiaEntity) {
        viewModelScope.launch {
            repository.actualizarMembresia(membresia)
        }
    }

    // Obtener una membresía específica para editarla
    suspend fun obtenerMembresiaPorId(id: Int): MembresiaEntity? {
        return repository.obtenerMembresiaPorId(id).first()
    }
}
