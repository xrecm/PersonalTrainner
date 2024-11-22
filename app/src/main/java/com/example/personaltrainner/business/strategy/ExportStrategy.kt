package com.example.personaltrainner.business.strategy

import android.content.Context
import com.example.personaltrainner.data.EjercicioEntity
import com.example.personaltrainner.data.RutinaEntity

interface ExportStrategy {
    fun exportar(context: Context, rutinas: List<RutinaEntity>, ejercicios: List<EjercicioEntity>): String
}