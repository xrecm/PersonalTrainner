package com.example.personaltrainner.business.strategy

import android.content.Context
import android.os.Environment
import com.example.personaltrainner.data.EjercicioEntity
import com.example.personaltrainner.data.RutinaEntity
import java.io.File
import java.io.FileWriter

class ExportarCSV : ExportStrategy {
    override fun exportar(context: Context, rutinas: List<RutinaEntity>, ejercicios: List<EjercicioEntity>): String {
        val directoryPath = context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS).toString()
        val fileName = "Rutinas_${System.currentTimeMillis()}.csv"
        val file = File(directoryPath, fileName)

        return try {
            val csvData = rutinas.joinToString("\n") { rutina ->
                val ejercicio = ejercicios.find { it.id == rutina.ejercicioId }?.nombre ?: "Desconocido"
                "${rutina.fecha}, $ejercicio, ${rutina.repeticiones}, ${rutina.series}"
            }
            file.writeText(csvData)
            "Archivo CSV generado correctamente: $fileName"
        } catch (e: Exception) {
            e.printStackTrace()
            "Error al generar el archivo CSV"
        }
    }
}
