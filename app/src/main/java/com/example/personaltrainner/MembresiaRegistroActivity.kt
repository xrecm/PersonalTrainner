import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.personaltrainner.business.MembresiaViewModel
import com.example.personaltrainner.data.AppDatabase
import com.example.personaltrainner.data.MembresiaRepository
import com.example.personaltrainner.ui.MembresiaRegistroScreen
import com.example.personaltrainner.ui.theme.PersonalTrainnerTheme

class MembresiaRegistroActivity : ComponentActivity() {
    private lateinit var membresiaViewModel: MembresiaViewModel
    private var membresiaId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Obtén el ID de membresía si es que se está editando
        membresiaId = intent.getIntExtra("membresiaId", -1).takeIf { it != -1 }

        // Inicializa ViewModel
        val database = AppDatabase.getDatabase(applicationContext)
        val repository = MembresiaRepository(database.membresiaDao())
        membresiaViewModel = MembresiaViewModel(repository)

        setContent {
            PersonalTrainnerTheme {
                MembresiaRegistroScreen(
                    viewModel = membresiaViewModel,
                    membresiaId = membresiaId,
                    onSave = { finish() }, // Volver a la lista después de guardar
                    onCancel = { finish() } // Cancelar y volver a la lista
                )
            }
        }
    }
}
