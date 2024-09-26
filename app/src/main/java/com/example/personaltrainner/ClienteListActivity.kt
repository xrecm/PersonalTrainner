package com.example.personaltrainner

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import com.example.personaltrainner.business.ClienteViewModel
import com.example.personaltrainner.business.ClienteViewModelFactory
import com.example.personaltrainner.business.MembresiaViewModel
import com.example.personaltrainner.business.MembresiaViewModelFactory
import com.example.personaltrainner.data.AppDatabase
import com.example.personaltrainner.data.ClienteRepository
import com.example.personaltrainner.data.MembresiaRepository
import com.example.personaltrainner.ui.ClienteListScreen
import com.example.personaltrainner.ui.theme.PersonalTrainnerTheme

class ClienteListActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize database and repositories
        val database = AppDatabase.getDatabase(applicationContext)
        val clienteRepository = ClienteRepository(database.clienteDao())
        val membresiaRepository = MembresiaRepository(database.membresiaDao())

        // Initialize ViewModels
        val clienteViewModel = ViewModelProvider(
            this,
            ClienteViewModelFactory(clienteRepository)
        ).get(ClienteViewModel::class.java)

        val membresiaViewModel = ViewModelProvider(
            this,
            MembresiaViewModelFactory(membresiaRepository)
        ).get(MembresiaViewModel::class.java)

        setContent {
            PersonalTrainnerTheme {
                ClienteListScreen(
                    viewModel = clienteViewModel,
                    membresiaViewModel = membresiaViewModel,
                    navigateToRegistro = {
                        startActivity(Intent(this@ClienteListActivity, ClienteRegistroActivity::class.java))
                    },
                    navigateToRutina = { clienteId ->
                        val intent = Intent(this@ClienteListActivity, RutinaActivity::class.java).apply {
                            putExtra("clienteId", clienteId)
                        }
                        startActivity(intent)
                    },
                    navigateToEditar = { clienteId ->
                        val intent = Intent(this@ClienteListActivity, ClienteRegistroActivity::class.java).apply {
                            putExtra("clienteId", clienteId)
                        }
                        startActivity(intent)
                    }
                )
            }
        }
    }
}
