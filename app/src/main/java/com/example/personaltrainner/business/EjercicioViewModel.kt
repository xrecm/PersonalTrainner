package com.example.personaltrainner.business

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.personaltrainner.data.EjercicioEntity
import com.example.personaltrainner.data.EjercicioRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class EjercicioViewModel(private val repository: EjercicioRepository) : ViewModel() {

    fun insertarEjercicio(ejercicio: EjercicioEntity) {
        viewModelScope.launch {
            repository.insertarEjercicio(ejercicio)
        }
    }
    fun obtenerTodosLosEjercicios(): Flow<List<EjercicioEntity>> {
        return repository.obtenerTodosLosEjercicios()
    }
    fun obtenerEjercicioPorId(ejercicioId: Int): Flow<EjercicioEntity?> {
        return repository.obtenerEjercicioPorId(ejercicioId)
    }
    fun actualizarEjercicio(ejercicio: EjercicioEntity) = viewModelScope.launch {
        repository.actualizarEjercicio(ejercicio)
    }
    fun eliminar(ejercicio: EjercicioEntity) {
        viewModelScope.launch {
            repository.eliminar(ejercicio)
        }
    }

}
