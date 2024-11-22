package com.example.personaltrainner.business.strategy

import android.content.Context
import com.example.personaltrainner.data.EjercicioEntity
import com.example.personaltrainner.data.RutinaEntity

class ExportContext(private var strategy: ExportStrategy) {
    fun setStrategy(strategy: ExportStrategy) {
        this.strategy = strategy
    }

    fun exportar(context: Context, rutinas: List<RutinaEntity>, ejercicios: List<EjercicioEntity>): String {
        return strategy.exportar(context, rutinas, ejercicios)
    }
}
