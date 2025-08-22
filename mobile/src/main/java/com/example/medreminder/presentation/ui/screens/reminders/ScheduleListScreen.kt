import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.text.style.TextOverflow
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.wear.compose.material.Text
import com.example.medreminder.presentation.ui.screens.medication.viewModel.MedicationListViewModel


@Composable
fun ScheduleListScreen(
    navController: NavController,
    viewModel: MedicationListViewModel = viewModel()
){
    val uiState by viewModel._uiState.collectAsState()
    if(uiState.isLoading){
        androidx.compose.material3.Text(
            text = "Cargando Horarios....༼ つ ◕_◕ ༽つ"
        )
    } else {
        androidx.compose.material3.Text(
            text = "Horarios Datos Cargados (*/ω＼*)"
        )
    }
}