package com.example.medreminder.presentation.ui.screens.medReminderForm // Ajusta tu paquete

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.medreminder.domain.model.MedSchedules // Asegúrate de que esta importación sea correcta
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalTime

// UI State para el formulario de horarios
data class ScheduleFormUiState(
    val time: LocalTime = LocalTime.NOON, // Un valor inicial sensato, ej: 12:00 PM
    val daysOfWeek: List<DayOfWeek> = emptyList(), // Usar List o Set, Set es más eficiente para 'contains'
    val startDate: LocalDate = LocalDate.now(),
    val endDate: LocalDate? = null, // La fecha de fin es opcional
    val isActive: Boolean = true, // Por defecto, un nuevo horario está activo

    // Campos para manejar errores de validación
    val timeError: String? = null,
    val daysError: String? = null,
    val datesError: String? = null // Para errores entre startDate y endDate
)

class ScheduleFormViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(ScheduleFormUiState())
    val uiState: StateFlow<ScheduleFormUiState> = _uiState.asStateFlow()

    fun updateTime(newTime: LocalTime) {
        _uiState.update { currentState ->
            currentState.copy(time = newTime, timeError = null)
        }
    }

    fun toggleDay(day: DayOfWeek) {
        _uiState.update { currentState ->
            val updatedDays = if (currentState.daysOfWeek.contains(day)) {
                currentState.daysOfWeek - day
            } else {
                // Mantener el orden de los días si se desea (opcional)
                (currentState.daysOfWeek + day).sortedBy { it.value }
            }
            currentState.copy(daysOfWeek = updatedDays, daysError = null)
        }
    }

    fun updateStartDate(newDate: LocalDate) {
        _uiState.update { currentState ->
            currentState.copy(startDate = newDate, datesError = null)
        }
    }

    fun updateEndDate(newDate: LocalDate?) {
        _uiState.update { currentState ->
            currentState.copy(endDate = newDate, datesError = null)
        }
    }



    /**
     * Limpia los mensajes de error mostrados en la UI.
     * Se puede llamar después de que el usuario haya visto el error.
     */
    fun clearValidationErrors() {
        _uiState.update { currentState ->
            currentState.copy(
                timeError = null,
                daysError = null,
                datesError = null
            )
        }
    }
    fun updateIsActive(activeState: Boolean) {
        _uiState.update { currentState ->
            currentState.copy(isActive = activeState)
        }
    }

    fun clearAllValidationErrors() {
        _uiState.update { currentState ->
            currentState.copy(
                timeError = null,
                daysError = null,
                datesError = null
            )
        }
    }

    /**
     * Valida el estado actual del formulario y, si es válido,
     * crea y devuelve un objeto MedSchedules.
     * Si no es válido, actualiza el uiState con los mensajes de error correspondientes
     * y devuelve null.
     */
    fun validateAndCreateSchedule(): MedSchedules? {
        val current = _uiState.value
        var isValid = true

        // Validación de días de la semana
        if (current.daysOfWeek.isEmpty()) {
            _uiState.update { it.copy(daysError = "Debes seleccionar al menos un día.") }
            isValid = false
        } else {
            // Limpiar error si previamente existía y ahora es válido
            if (current.daysError != null) _uiState.update { it.copy(daysError = null) }
        }

        // Validación de fechas (endDate no puede ser anterior a startDate)
        if (current.endDate != null && current.endDate.isBefore(current.startDate)) {
            _uiState.update { it.copy(datesError = "La fecha de fin no puede ser anterior a la fecha de inicio.") }
            isValid = false
        } else {
            // Limpiar error si previamente existía y ahora es válido
            if (current.datesError != null) _uiState.update { it.copy(datesError = null) }
        }

        // Aquí podrías añadir más validaciones si fueran necesarias (ej. para la hora, aunque
        // si usas un TimePicker, usualmente ya te da una LocalTime válida).

        return if (isValid) {
            MedSchedules(
                // IDs se asignarán más adelante o por el repositorio.
                // Para este ViewModel, solo nos preocupa crear un objeto de horario válido
                // con los datos del formulario.
                id = 0, // Placeholder, el ID real vendrá de la base de datos
                medicationId = 0, // Placeholder, se asignará al guardar el medicamento
                time = current.time,
                daysOfWeek = current.daysOfWeek,
                startDate = current.startDate,
                endDate = current.endDate,
                isActive = current.isActive
            )
        } else {
            null // La validación falló
        }
    }
}
