package com.example.personaltrainner.ui

import com.example.personaltrainner.data.MembresiaEntity
import java.text.SimpleDateFormat
import java.util.*

fun calcularFechaFin(membresia: MembresiaEntity?, fechaInicio: String): String? {
    if (membresia == null || fechaInicio.isEmpty()) return null

    val formatoFecha = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    val fechaInicioDate = formatoFecha.parse(fechaInicio) ?: return null

    val calendario = Calendar.getInstance().apply { time = fechaInicioDate }
    when (membresia.tipo) {
        "Semanal" -> calendario.add(Calendar.DAY_OF_YEAR, 7)
        "Mensual" -> calendario.add(Calendar.DAY_OF_YEAR, 30)
        "3 Meses" -> calendario.add(Calendar.DAY_OF_YEAR, 90)
        "6 Meses" -> calendario.add(Calendar.DAY_OF_YEAR, 180)
        "Anual" -> calendario.add(Calendar.DAY_OF_YEAR, 365)
    }
    return formatoFecha.format(calendario.time)
}
