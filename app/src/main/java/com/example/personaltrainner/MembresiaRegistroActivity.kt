package com.example.personaltrainner

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import com.example.personaltrainner.business.MembresiaViewModel
import com.example.personaltrainner.business.MembresiaViewModelFactory
import com.example.personaltrainner.data.AppDatabase
import com.example.personaltrainner.data.MembresiaRepository
import com.example.personaltrainner.ui.MembresiaRegistroScreen
import com.example.personaltrainner.ui.theme.PersonalTrainnerTheme

class MembresiaRegistroActivity : ComponentActivity() {

    private lateinit var membresiaViewModel: MembresiaViewModel
    private var membresiaId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        membresiaId = intent.getIntExtra("membresiaId", -1).takeIf { it != -1 }

        val database = AppDatabase.getDatabase(applicationContext)
        val repository = MembresiaRepository(database.membresiaDao())
        val factory = MembresiaViewModelFactory(repository)

        membresiaViewModel = ViewModelProvider(this, factory)[MembresiaViewModel::class.java]

        setContent {
            PersonalTrainnerTheme {
                MembresiaRegistroScreen(
                    viewModel = membresiaViewModel,
                    membresiaId = membresiaId,
                    onSave = { finish() }, // Vuelve a la lista después de guardar
                    onCancel = { finish() } // Cancelar y volver a la lista
                )
            }
        }
    }
}

