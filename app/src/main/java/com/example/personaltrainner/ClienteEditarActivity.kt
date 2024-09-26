package com.example.personaltrainner

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModelProvider
import com.example.personaltrainner.business.ClienteViewModel
import com.example.personaltrainner.business.ClienteViewModelFactory
import com.example.personaltrainner.business.MembresiaViewModel
import com.example.personaltrainner.business.MembresiaViewModelFactory
import com.example.personaltrainner.data.AppDatabase
import com.example.personaltrainner.data.ClienteRepository
import com.example.personaltrainner.data.MembresiaRepository
import com.example.personaltrainner.ui.ClienteEditarScreen
import com.example.personaltrainner.ui.theme.PersonalTrainnerTheme

class ClienteEditarActivity : ComponentActivity() {
    private var clienteId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        clienteId = intent.getIntExtra("clienteId", -1)
        if (clienteId == -1) {
            finish()
            return
        }

        val database = AppDatabase.getDatabase(applicationContext)
        val clienteRepository = ClienteRepository(database.clienteDao())
        val clienteViewModel = ViewModelProvider(this, ClienteViewModelFactory(clienteRepository))
            .get(ClienteViewModel::class.java)

        val membresiaRepository = MembresiaRepository(database.membresiaDao())
        val membresiaViewModel = ViewModelProvider(this, MembresiaViewModelFactory(membresiaRepository))
            .get(MembresiaViewModel::class.java)

        setContent {
            PersonalTrainnerTheme {

                // Observamos los datos usando collectAsState para Flow o observeAsState para LiveData
                val cliente = clienteViewModel.obtenerClientePorId(clienteId).collectAsState(initial = null).value
                val membresias = membresiaViewModel.obtenerTodasLasMembresias().collectAsState(initial = emptyList()).value

                if (cliente != null) {
                    ClienteEditarScreen(
                        cliente = cliente,           // Pasa el cliente cargado
                        membresias = membresias,     // Pasa la lista de membresías
                        viewModel = clienteViewModel,
                        onSave = { finish() },       // Vuelve a la lista después de actualizar
                        onCancel = { finish() }      // Vuelve sin cambios
                    )
                } else {
                    // Mostrar un indicador de carga o mensaje
                    Text("Cargando datos...")
                }
            }
        }
    }
}


