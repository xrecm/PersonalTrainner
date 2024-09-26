package com.example.personaltrainner

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.lifecycleScope
import com.example.personaltrainner.business.PlanSemanalViewModel
import com.example.personaltrainner.data.AppDatabase
import com.example.personaltrainner.data.PlanSemanalRepository
import com.example.personaltrainner.ui.PlanSemanalScreen
import com.example.personaltrainner.ui.theme.PersonalTrainnerTheme
import kotlinx.coroutines.launch

class PlanSemanalActivity : ComponentActivity() {

    private lateinit var planSemanalViewModel: PlanSemanalViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicializar la base de datos y el repositorio
        val database = AppDatabase.getDatabase(applicationContext)
        val repository = PlanSemanalRepository(database.planSemanalDao())

        // Inicializar el ViewModel
        planSemanalViewModel = PlanSemanalViewModel(repository)

        // Obtener clientes y ejercicios
        lifecycleScope.launch {
            val clientes = database.clienteDao().obtenerTodosLosClientes() // Flow<List<ClienteEntity>>
            val ejercicios = database.ejercicioDao().obtenerTodosLosEjercicios() // Flow<List<EjercicioEntity>>

            clientes.collect { clientesList ->
                ejercicios.collect { ejerciciosList ->
                    setContent {
                        PersonalTrainnerTheme {
                            PlanSemanalScreen(
                                viewModel = planSemanalViewModel,
                                clientes = clientesList,
                                ejercicios = ejerciciosList
                            )
                        }
                    }
                }
            }
        }
    }
}
