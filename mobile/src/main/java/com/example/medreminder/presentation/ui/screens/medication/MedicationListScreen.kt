import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.medreminder.presentation.ui.screens.medication.viewModel.MedicationListViewModel
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

@Composable
fun MedicationListScreen(
    navController: NavHostController,
    viewModel: MedicationListViewModel = viewModel()
){
    val uiState by viewModel._uiState.collectAsState()
    if(uiState.isLoading){
        Text(
            text = "Cargando.... Medicamentos ╰(*°▽°*)╯"
        )
    } else {
        Text(
            text = "Medicamentos Cargados (*/ω＼*)"
        )
    }
}