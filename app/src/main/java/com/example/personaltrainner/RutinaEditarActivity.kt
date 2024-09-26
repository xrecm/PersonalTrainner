package com.example.personaltrainner

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import com.example.personaltrainner.business.RutinaViewModel
import com.example.personaltrainner.business.RutinaViewModelFactory
import com.example.personaltrainner.data.AppDatabase
import com.example.personaltrainner.data.RutinaRepository
import com.example.personaltrainner.ui.RutinaEditarScreen
import com.example.personaltrainner.ui.theme.PersonalTrainnerTheme

class RutinaEditarActivity : ComponentActivity() {
    private var rutinaId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        rutinaId = intent.getIntExtra("rutinaId", -1)

        val database = AppDatabase.getDatabase(applicationContext)
        val repository = RutinaRepository(database.rutinaDao())
        val viewModel = ViewModelProvider(this, RutinaViewModelFactory(repository))
            .get(RutinaViewModel::class.java)

        setContent {
            PersonalTrainnerTheme {
                RutinaEditarScreen(
                    rutinaId = rutinaId,
                    viewModel = viewModel,
                    clientes = listOf(), // Pasar la lista de clientes
                    ejercicios = listOf(), // Pasar la lista de ejercicios
                    onSave = { finish() },
                    onCancel = { finish() }
                )
            }
        }
    }
}
