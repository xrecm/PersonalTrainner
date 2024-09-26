package com.example.personaltrainner

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.personaltrainner.ui.ClienteRegistroScreen
import com.example.personaltrainner.ui.theme.PersonalTrainnerTheme
import com.example.personaltrainner.business.ClienteViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.personaltrainner.business.ClienteViewModelFactory
import com.example.personaltrainner.data.AppDatabase
import com.example.personaltrainner.data.ClienteRepository

class ClienteRegistroActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicializa la base de datos y el repositorio
        val database = AppDatabase.getDatabase(applicationContext)
        val repository = ClienteRepository(database.clienteDao())
        val clienteViewModel = ViewModelProvider(this, ClienteViewModelFactory(repository))
            .get(ClienteViewModel::class.java)

        setContent {
            PersonalTrainnerTheme {
                ClienteRegistroScreen(viewModel = clienteViewModel)
            }
        }
    }
}
