package com.example.personaltrainner

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import com.example.personaltrainner.business.ClienteViewModel
import com.example.personaltrainner.business.ClienteViewModelFactory
import com.example.personaltrainner.data.AppDatabase
import com.example.personaltrainner.data.ClienteRepository
import com.example.personaltrainner.ui.ClienteListScreen
import com.example.personaltrainner.ui.theme.PersonalTrainnerTheme

class ClienteListActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicializa la base de datos y el repositorio
        val database = AppDatabase.getDatabase(applicationContext)
        val repository = ClienteRepository(database.clienteDao())

        // Crear el ViewModel usando el ClienteViewModelFactory
        val clienteViewModel = ViewModelProvider(
            this,
            ClienteViewModelFactory(repository)
        ).get(ClienteViewModel::class.java)

        setContent {
            PersonalTrainnerTheme {
                ClienteListScreen(
                    viewModel = clienteViewModel,
                    navigateToRegistro = {
                        startActivity(Intent(this@ClienteListActivity, ClienteRegistroActivity::class.java))
                    }
                )
            }
        }
    }
}
