package com.example.personaltrainner.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "clientes")
data class ClienteEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nombre: String,
    val apellido: String,
    val telefono: Int,
    val edad: Int,
    val sexo: String,
    val tamaño: Float,
    val peso: Float
)