package com.example.personaltrainner.business

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.personaltrainner.data.EjercicioEntity
import com.example.personaltrainner.data.EjercicioRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class EjercicioViewModel(private val repository: EjercicioRepository) : ViewModel() {

    // Función para insertar un nuevo ejercicio en la base de datos
    fun insertarEjercicio(ejercicio: EjercicioEntity) {
        viewModelScope.launch {
            repository.insertarEjercicio(ejercicio)
        }
    }

    // Función para obtener todos los ejercicios desde la base de datos
    fun obtenerTodosLosEjercicios(): Flow<List<EjercicioEntity>> {
        return repository.obtenerTodosLosEjercicios()
    }
}
