package com.example.personaltrainner.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "clientes",
    foreignKeys = [
        ForeignKey(entity = MembresiaEntity::class, parentColumns = ["id"], childColumns = ["membresiaId"])
    ]
)
data class ClienteEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nombre: String,
    val apellido: String,
    val telefono: Int,
    val edad: Int,
    val sexo: String,
    val tamaño: Float,
    val peso: Float,
    val membresiaId: Int?,  // Clave foránea a MembresiaEntity, puede ser nula
    val fechaInicioMembresia: String?, // Fecha de inicio de la membresía (opcional)
    val fechaFinMembresia: String? // Fecha de fin calculada automáticamente (opcional)
)