package com.example.personaltrainner.business.strategy

import android.content.Context
import android.os.Environment
import com.example.personaltrainner.data.EjercicioEntity
import com.example.personaltrainner.data.RutinaEntity
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.File
import java.io.FileOutputStream

class ExportarExcel : ExportStrategy {
    override fun exportar(context: Context, rutinas: List<RutinaEntity>, ejercicios: List<EjercicioEntity>): String {
        val directoryPath = context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS).toString()
        val fileName = "Rutinas_${System.currentTimeMillis()}.xlsx"
        val file = File(directoryPath, fileName)

        return try {
            val workbook = HSSFWorkbook()
            val sheet = workbook.createSheet("Rutinas")

            val headerRow = sheet.createRow(0)
            headerRow.createCell(0).setCellValue("Fecha")
            headerRow.createCell(1).setCellValue("Ejercicio")
            headerRow.createCell(2).setCellValue("Repeticiones")
            headerRow.createCell(3).setCellValue("Series")

            rutinas.forEachIndexed { index, rutina ->
                val row = sheet.createRow(index + 1)
                val ejercicio = ejercicios.find { it.id == rutina.ejercicioId }?.nombre ?: "Desconocido"
                row.createCell(0).setCellValue(rutina.fecha.toString())
                row.createCell(1).setCellValue(ejercicio)
                row.createCell(2).setCellValue(rutina.repeticiones.toDouble())
                row.createCell(3).setCellValue(rutina.series.toDouble())
            }

            FileOutputStream(file).use { fos ->
                workbook.write(fos)
            }
            "Archivo Excel generado correctamente: $fileName"
        } catch (e: Exception) {
            e.printStackTrace()
            "Error al generar el archivo Excel"
        }
    }
}
