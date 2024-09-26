package com.example.personaltrainner

import VerRutinaScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.personaltrainner.ui.theme.PersonalTrainnerTheme
import com.example.personaltrainner.business.RutinaViewModel
import com.example.personaltrainner.business.EjercicioViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.personaltrainner.business.RutinaViewModelFactory
import com.example.personaltrainner.business.EjercicioViewModelFactory
import com.example.personaltrainner.data.AppDatabase
import com.example.personaltrainner.data.RutinaRepository
import com.example.personaltrainner.data.EjercicioRepository

class VerRutinaActivity : ComponentActivity() {
    private var clienteId: Int = -1
    private lateinit var clienteNombre: String
    private lateinit var clienteApellido: String
    private lateinit var rutinaViewModel: RutinaViewModel
    private lateinit var ejercicioViewModel: EjercicioViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Obtener los datos del Intent
        clienteId = intent.getIntExtra("clienteId", -1)
        clienteNombre = intent.getStringExtra("clienteNombre") ?: ""
        clienteApellido = intent.getStringExtra("clienteApellido") ?: ""

        if (clienteId == -1) {
            finish() // Si no se recibió un clienteId válido, cerrar la actividad
            return
        }

        // Inicializar ViewModel para Rutina
        val database = AppDatabase.getDatabase(applicationContext)
        val rutinaRepository = RutinaRepository(database.rutinaDao())
        rutinaViewModel = ViewModelProvider(this, RutinaViewModelFactory(rutinaRepository))
            .get(RutinaViewModel::class.java)

        // Inicializar ViewModel para Ejercicio
        val ejercicioRepository = EjercicioRepository(database.ejercicioDao())
        ejercicioViewModel = ViewModelProvider(this, EjercicioViewModelFactory(ejercicioRepository))
            .get(EjercicioViewModel::class.java)

        // Cargar la pantalla
        setContent {
            PersonalTrainnerTheme {
                VerRutinaScreen(
                    clienteId = clienteId,
                    clienteNombre = clienteNombre,
                    clienteApellido = clienteApellido,
                    rutinaViewModel = rutinaViewModel,
                    ejercicioViewModel = ejercicioViewModel
                )
            }
        }
    }
}
