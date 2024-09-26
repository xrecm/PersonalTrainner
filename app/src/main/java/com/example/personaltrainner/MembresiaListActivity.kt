import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.personaltrainner.ui.MembresiaListScreen
import com.example.personaltrainner.business.MembresiaViewModel
import com.example.personaltrainner.data.AppDatabase
import com.example.personaltrainner.data.MembresiaRepository
import com.example.personaltrainner.ui.theme.PersonalTrainnerTheme

class MembresiaListActivity : ComponentActivity() {
    private lateinit var membresiaViewModel: MembresiaViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicializar el ViewModel
        val database = AppDatabase.getDatabase(applicationContext)
        val repository = MembresiaRepository(database.membresiaDao())
        membresiaViewModel = MembresiaViewModel(repository)

        setContent {
            PersonalTrainnerTheme {
                MembresiaListScreen(
                    viewModel = membresiaViewModel,
                    navigateToRegistro = {
                        val intent = Intent(this, MembresiaRegistroActivity::class.java)
                        startActivity(intent) // Navegar a la pantalla de registro de membresías
                    }
                )
            }
        }
    }
}
