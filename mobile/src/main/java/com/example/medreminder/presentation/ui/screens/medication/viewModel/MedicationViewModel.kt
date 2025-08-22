package com.example.medreminder.presentation.ui.screens.medication.viewModel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.medreminder.domain.model.MedSchedules
import com.example.medreminder.domain.model.Medication
import com.example.medreminder.domain.model.MedicationForm
import com.example.medreminder.domain.model.data.MedicationRepository
import com.example.medreminder.domain.model.data.MedicationRepositoryImpl
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalTime

// UI State para la lista de medicamentos
data class MedicationListUiState(
    val medications: List<Medication> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
)

// UI State para agregar/editar medicamento
data class AddMedicationUiState(
    val name: String = "",
    val dosage: String = "",
    val selectedForm: MedicationForm = MedicationForm.PILL,
    val schedules: List<MedSchedules> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val isSuccess: Boolean = false,
)


@Suppress("UNCHECKED_CAST")
class MedicationListViewModel(
    private val repository: MedicationRepository = MedicationRepositoryImpl(),
) : ViewModel() {

    val _uiState = MutableStateFlow(MedicationListUiState())

    init {
        loadMedications()
    }

    fun loadMedications() {
        viewModelScope.launch { // Inicia una coroutina
            _uiState.update { it.copy(isLoading = true, error = null) } // Actualiza estado a cargando y limpia errores
            try {
                val medicationsList = repository.getAllMedications() // Esto ejecutará la función suspendida
                delay(1000)
                _uiState.update {
                    it.copy(isLoading = false, medications = medicationsList as List<Medication>, error = null)
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(isLoading = false, error = "Error al cargar medicamentos: ${e.message}")
                }
            }
        }
    }

    fun deleteMedication(id: Int) {
        viewModelScope.launch {
            try {
                repository.deleteMedication(id)
                loadMedications()
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(error = e.message)
            }
        }
    }

    fun toggleMedicationActive(medication: Medication) {
        viewModelScope.launch {
            try {
                val updatedMedication = medication.copy(isActive = !medication.isActive)
                repository.updateMedication(updatedMedication)
                loadMedications()
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(error = e.message)
            }
        }
    }

}

// ViewModel para agregar medicamento
class AddMedicationViewModel(
    private val repository: MedicationRepository = MedicationRepositoryImpl(),
    private val savedStateHandle: SavedStateHandle,
    ) : ViewModel() {

    private val _uiState = MutableStateFlow(AddMedicationUiState())
    companion object {
        const val NEW_SCHEDULE_RESULT_KEY = "new_schedule_result_key" // ¡Aquí está!
    }

    init {
        // Observar el resultado de ScheduleFormScreen
        viewModelScope.launch {
            savedStateHandle.getStateFlow<MedSchedules?>(NEW_SCHEDULE_RESULT_KEY, null)
                .collect { newSchedule ->
                    if (newSchedule != null) {
                        // Añadir el nuevo horario a la lista en AddMedicationUiState
                        internalAddSchedule(newSchedule)
                        // Limpiar el resultado del SavedStateHandle para que no se procese de nuevo
                        savedStateHandle[NEW_SCHEDULE_RESULT_KEY] = null
                    }
                }
        }
    }
    fun updateName(name: String) {
        _uiState.value = _uiState.value.copy(name = name, error = null)
    }
    private fun internalAddSchedule(schedule: MedSchedules) {
        _uiState.update { currentState ->
            currentState.copy(schedules = currentState.schedules + schedule, error = null)
        }
    }
    fun updateDosage(dosage: String) {
        _uiState.value = _uiState.value.copy(dosage = dosage, error = null)
    }

    fun updateForm(form: MedicationForm) {
        _uiState.value = _uiState.value.copy(selectedForm = form, error = null)
    }

    fun addSchedule(active:Boolean, time: LocalTime, days: List<DayOfWeek>, sartDate: LocalDate, endDate: LocalDate) {
        val newSchedule = MedSchedules(
            id = 0,
            medicationId = 0,
            time = time,
            daysOfWeek = days,
            startDate = sartDate,
            endDate = endDate,
            isActive = active
        )
        val currentSchedules = _uiState.value.schedules.toMutableList()
        currentSchedules.add(newSchedule)
        _uiState.value = _uiState.value.copy(schedules = currentSchedules)
    }

    fun removeSchedule(index: Int) {
        val currentSchedules = _uiState.value.schedules.toMutableList()
        if (index in 0 until currentSchedules.size) {
            currentSchedules.removeAt(index)
            _uiState.value = _uiState.value.copy(schedules = currentSchedules)
        }
    }

    fun saveMedication() {
        viewModelScope.launch {
            val state = _uiState.value

            // Validaciones
            if (state.name.isBlank()) {
                _uiState.value = state.copy(error = "El nombre del medicamento es requerido")
                return@launch
            }

            if (state.dosage.isBlank()) {
                _uiState.value = state.copy(error = "La dosis es requerida")
                return@launch
            }

            if (state.schedules.isEmpty()) {
                _uiState.value = state.copy(error = "Debe agregar al menos un horario")
                return@launch
            }

            _uiState.value = state.copy(isLoading = true, error = null)

            try {
                val medication = Medication(
                    id = 0, // Se asignará en el repository
                    name = state.name.trim(),
                    dosage = state.dosage.trim(),
                    isActive = true,
                    schedule = emptyList(),
                    form = state.selectedForm
                )

                repository.insertMedication(medication)
                _uiState.value = state.copy(
                    isLoading = false,
                    isSuccess = true
                )
            } catch (e: Exception) {
                _uiState.value = state.copy(
                    isLoading = false,
                    error = e.message ?: "Error desconocido"
                )
            }
        }
    }

    fun resetState() {
        _uiState.value = AddMedicationUiState()
    }
}

