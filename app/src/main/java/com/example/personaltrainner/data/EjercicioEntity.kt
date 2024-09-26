package com.example.personaltrainner.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ejercicios")
data class EjercicioEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nombre: String,
    val descripcion: String,
    val tipo: String // Aquí almacenamos las partes del cuerpo afectadas
)
