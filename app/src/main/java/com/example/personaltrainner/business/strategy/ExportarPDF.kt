package com.example.personaltrainner.business.strategy

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import android.os.Environment
import com.example.personaltrainner.data.EjercicioEntity
import com.example.personaltrainner.data.RutinaEntity
import java.io.File
import java.io.FileOutputStream

class ExportarPDF : ExportStrategy {
    override fun exportar(context: Context, rutinas: List<RutinaEntity>, ejercicios: List<EjercicioEntity>): String {
        val pdfDocument = PdfDocument()
        val paint = Paint()

        val pageInfo = PdfDocument.PageInfo.Builder(595, 842, 1).create()
        var page = pdfDocument.startPage(pageInfo)
        var canvas = page.canvas

        var yPosition = 50f
        paint.textSize = 20f
        paint.isFakeBoldText = true
        canvas.drawText("Reporte de Rutinas", 20f, yPosition, paint)

        yPosition += 40f
        paint.textSize = 14f
        paint.isFakeBoldText = false

        for (rutina in rutinas) {
            if (yPosition > 800f) {
                pdfDocument.finishPage(page)
                page = pdfDocument.startPage(pageInfo)
                canvas = page.canvas
                yPosition = 50f
            }
            val ejercicio = ejercicios.find { it.id == rutina.ejercicioId }?.nombre ?: "Desconocido"
            canvas.drawText("Fecha: ${rutina.fecha}", 20f, yPosition, paint)
            yPosition += 20f
            canvas.drawText("Ejercicio: $ejercicio", 20f, yPosition, paint)
            yPosition += 20f
            canvas.drawText("Repeticiones: ${rutina.repeticiones}, Series: ${rutina.series}", 20f, yPosition, paint)
            yPosition += 30f
        }

        pdfDocument.finishPage(page)

        val directoryPath = context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS).toString()
        val fileName = "Rutinas_${System.currentTimeMillis()}.pdf"
        val file = File(directoryPath, fileName)

        return try {
            pdfDocument.writeTo(FileOutputStream(file))
            "PDF generado correctamente: $fileName"
        } catch (e: Exception) {
            e.printStackTrace()
            "Error al generar el PDF"
        } finally {
            pdfDocument.close()
        }
    }
}
