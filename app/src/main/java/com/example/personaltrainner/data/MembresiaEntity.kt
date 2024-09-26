package com.example.personaltrainner.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "membresia")
data class MembresiaEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val tipo: String,
    val nombre: String,
    val descripcion: String,
    val precio: Double
)
