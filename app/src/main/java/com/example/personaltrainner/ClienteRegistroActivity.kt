package com.example.personaltrainner

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModelProvider
import com.example.personaltrainner.business.ClienteViewModel
import com.example.personaltrainner.business.ClienteViewModelFactory
import com.example.personaltrainner.business.MembresiaViewModel
import com.example.personaltrainner.business.MembresiaViewModelFactory
import com.example.personaltrainner.data.AppDatabase
import com.example.personaltrainner.data.ClienteRepository
import com.example.personaltrainner.data.MembresiaRepository
import com.example.personaltrainner.ui.ClienteRegistroScreen
import com.example.personaltrainner.ui.theme.PersonalTrainnerTheme

class ClienteRegistroActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicializa la base de datos y los repositorios
        val database = AppDatabase.getDatabase(applicationContext)
        val clienteRepository = ClienteRepository(database.clienteDao())
        val clienteViewModel = ViewModelProvider(this, ClienteViewModelFactory(clienteRepository))
            .get(ClienteViewModel::class.java)

        val membresiaRepository = MembresiaRepository(database.membresiaDao())
        val membresiaViewModel = ViewModelProvider(this, MembresiaViewModelFactory(membresiaRepository))
            .get(MembresiaViewModel::class.java)

        setContent {
            PersonalTrainnerTheme {
                // Obtener la lista de membresías como flujo de datos
                val membresia by membresiaViewModel.obtenerTodasLasMembresias().collectAsState(initial = emptyList())

                // Pasar la lista de membresías a la pantalla de registro de cliente
                ClienteRegistroScreen(
                    viewModel = clienteViewModel,
                    membresias = membresia
                )
            }
        }
    }
}
