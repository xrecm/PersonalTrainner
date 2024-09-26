package com.example.personaltrainner.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PlanSemanalDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarPlanSemanal(planSemanal: PlanSemanalEntity)

    @Query("SELECT * FROM planes_semanales WHERE clienteId = :clienteId")
    fun obtenerTodosLosPlanes(clienteId: Int): Flow<List<PlanSemanalEntity>>

    @Query("DELETE FROM planes_semanales WHERE id = :planId")
    suspend fun eliminarPlan(planId: Int)
}
