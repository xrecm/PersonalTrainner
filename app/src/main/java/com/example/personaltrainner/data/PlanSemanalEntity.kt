package com.example.personaltrainner.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.Date

@Entity(
    tableName = "planes_semanales",
    foreignKeys = [
        ForeignKey(entity = ClienteEntity::class, parentColumns = ["id"], childColumns = ["clienteId"]),
        ForeignKey(entity = EjercicioEntity::class, parentColumns = ["id"], childColumns = ["ejercicioId"])
    ]
)
data class PlanSemanalEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val clienteId: Int,
    val ejercicioId: Int,
    val fecha: Date,
    val repeticiones: Int,
    val series: Int
)
