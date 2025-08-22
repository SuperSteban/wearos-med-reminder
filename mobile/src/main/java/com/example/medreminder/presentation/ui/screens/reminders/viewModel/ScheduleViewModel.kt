import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.medreminder.domain.model.MedSchedules
import com.example.medreminder.domain.model.Medication
import com.example.medreminder.domain.model.data.ScheduleRepository
import com.example.medreminder.domain.model.data.ScheduleRepositoryImpl
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalTime

data class ScheduleListUiState(
    val schedules: List<MedSchedules> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
)

data class AddScheduleUiState(
    //val id: Int ,
    //val medicationId: Int,
    val time: LocalTime = LocalTime.now(),
    val daysOfWeek: List<DayOfWeek> = listOf<DayOfWeek>(DayOfWeek.MONDAY, DayOfWeek.THURSDAY),
    val startDate: LocalDate = LocalDate.now(),
    val endDate: LocalDate? = LocalDate.now().plusDays(30),
    val isActive: Boolean = true
)

class ScheduleViewModel(
    private val repository: ScheduleRepository = ScheduleRepositoryImpl()
): ViewModel(
) {
    val _uiState = MutableStateFlow(ScheduleListUiState())
    init{
        loadSchedules()
    }
    fun loadSchedules() {
        viewModelScope.launch { // Inicia una coroutina
            _uiState.update { it.copy(isLoading = true, error = null) } // Actualiza estado a cargando y limpia errores
            try {
                val scheduleList = repository.getAllSchedules() // Esto ejecutará la función suspendida
                delay(2000)
                _uiState.update {
                    it.copy(isLoading = false, schedules = scheduleList as List<MedSchedules>, error = null)
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(isLoading = false, error = "Error al cargar medicamentos: ${e.message}")
                }
            }
        }
    }
    fun loadSchedulesByMedicationId() {

    }
}