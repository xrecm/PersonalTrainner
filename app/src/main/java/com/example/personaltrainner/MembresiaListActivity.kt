package com.example.personaltrainner

import MembresiaListScreen
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import com.example.personaltrainner.business.MembresiaViewModel
import com.example.personaltrainner.business.MembresiaViewModelFactory
import com.example.personaltrainner.data.AppDatabase
import com.example.personaltrainner.data.MembresiaRepository
import com.example.personaltrainner.ui.theme.PersonalTrainnerTheme

class MembresiaListActivity : ComponentActivity() {

    private lateinit var membresiaViewModel: MembresiaViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database = AppDatabase.getDatabase(applicationContext)
        val repository = MembresiaRepository(database.membresiaDao())
        val membresiaViewModel = ViewModelProvider(this, MembresiaViewModelFactory(repository)
        )[MembresiaViewModel::class.java]

        setContent {
            PersonalTrainnerTheme {
                MembresiaListScreen(
                    viewModel = membresiaViewModel,
                    navigateToRegistro = {
                        startActivity(Intent(this, MembresiaRegistroActivity::class.java))
                    },
                    navigateToEditar = { membresiaId ->
                        val intent = Intent(this, MembresiaRegistroActivity::class.java)
                        intent.putExtra("membresiaId", membresiaId)
                        startActivity(intent)
                    }
                )
            }
        }
    }
}
