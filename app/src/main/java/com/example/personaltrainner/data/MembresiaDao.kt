package com.example.personaltrainner.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface MembresiaDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarMembresia(membresia: MembresiaEntity)

    @Query("SELECT * FROM membresia")
    fun obtenerTodasLasMembresias(): Flow<List<MembresiaEntity>>

    @Delete
    suspend fun eliminarMembresia(membresia: MembresiaEntity)

    @Update
    suspend fun actualizarMembresia(membresia: MembresiaEntity)

    @Query("SELECT * FROM membresia WHERE id = :id LIMIT 1")
    fun obtenerMembresiaPorId(id: Int): Flow<MembresiaEntity?>
}
