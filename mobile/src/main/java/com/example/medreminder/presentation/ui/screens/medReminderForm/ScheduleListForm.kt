package com.example.medreminder.presentation.ui.screens.medReminderForm // O la ruta correcta para esta pantalla

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.medreminder.presentation.ui.screens.medicationForm.viewModel.AddMedicationViewModel // Para la clave del resultado
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale



// --- Definiciones de Composables que necesitas crear (TimePickerDialog, DatePickerDialog) ---
@Composable
fun TimePickerDialog(
    // Ejemplo de firma, la implementación es compleja
    onDismissRequest: () -> Unit,
    onTimeSelected: (LocalTime) -> Unit,
    initialTime: LocalTime = LocalTime.now(),
) {
    // Implementación de tu diálogo de selección de tiempo
    // Podrías usar un AlertDialog con TextFields para horas/minutos,
    // o buscar librerías de terceros para un picker más completo.
    // Por simplicidad, aquí solo un placeholder:
    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = { Text("Seleccionar Hora") },
        text = {
            // Aquí iría tu UI de selección de tiempo
            Text("Placeholder para Time Picker. Implementa tu selector aquí.")
            Button(onClick = { onTimeSelected(LocalTime.of(10, 30)); onDismissRequest() }) {
                Text("Seleccionar 10:30 (Ejemplo)")
            }
        },
        confirmButton = {
            TextButton(onClick = onDismissRequest) { Text("Cerrar") }
        }
    )
}

@Composable
fun DatePickerDialog(
    // Ejemplo de firma
    onDismissRequest: () -> Unit,
    onDateSelected: (LocalDate) -> Unit,
    initialDate: LocalDate = LocalDate.now(),
) {
    // Implementación de tu diálogo de selección de fecha
    // Similar a TimePickerDialog, puedes usar AlertDialog o librerías.
    // Placeholder:
    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = { Text("Seleccionar Fecha") },
        text = {
            Text("Placeholder para Date Picker. Implementa tu selector aquí.")
            Button(onClick = { onDateSelected(LocalDate.now().plusDays(1)); onDismissRequest() }) {
                Text("Seleccionar Mañana (Ejemplo)")
            }
        },
        confirmButton = {
            TextButton(onClick = onDismissRequest) { Text("Cerrar") }
        }
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScheduleFormScreen(
    navController: NavHostController,
    viewModel: ScheduleFormViewModel = viewModel(), // ViewModel para ESTA pantalla
) {
    val scheduleState by viewModel.uiState.collectAsState()
    var showTimePicker by remember { mutableStateOf(false) }
    var showStartDatePicker by remember { mutableStateOf(false) }
    var showEndDatePicker by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }

    // Para mostrar errores de validación del ScheduleFormViewModel
    LaunchedEffect(scheduleState.timeError, scheduleState.daysError, scheduleState.datesError) {
        val error = scheduleState.timeError ?: scheduleState.daysError ?: scheduleState.datesError
        error?.let {
            snackbarHostState.showSnackbar(
                message = it,
                duration = SnackbarDuration.Short
            )
            viewModel.clearValidationErrors() // Añade una función en ViewModel para limpiar errores después de mostrarlos
        }
    }


    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text(text = "Nuevo Horario") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // --- Selector de Hora ---
            OutlinedButton(
                onClick = { showTimePicker = true },
                modifier = Modifier.fillMaxWidth()
            ) {
                val formattedTime = scheduleState.time.format(DateTimeFormatter.ofPattern("HH:mm"))
                Text(text = "Hora seleccionada: $formattedTime")
            }

            // --- Selector de Días ---
            Text("Días de la semana:", style = MaterialTheme.typography.titleMedium)
            DaySelector(
                selectedDays = scheduleState.daysOfWeek,
                onDayToggled = viewModel::toggleDay // Asume que toggleDay existe en tu VM
            )
            if (scheduleState.daysError != null) {
                Text(scheduleState.daysError!!, color = MaterialTheme.colorScheme.error)
            }


            // --- Selector de Fecha de Inicio ---
            OutlinedButton(
                onClick = { showStartDatePicker = true },
                modifier = Modifier.fillMaxWidth()
            ) {
                val formattedDate = scheduleState.startDate.format(DateTimeFormatter.ofLocalizedDate(java.time.format.FormatStyle.MEDIUM))
                Text(text = "Fecha de inicio: $formattedDate")
            }

            // --- Selector de Fecha de Fin (Opcional) ---
            OutlinedButton(
                onClick = { showEndDatePicker = true },
                modifier = Modifier.fillMaxWidth()
            ) {
                val formattedDate = scheduleState.endDate?.format(DateTimeFormatter.ofLocalizedDate(java.time.format.FormatStyle.MEDIUM)) ?: "Sin definir"
                Text(text = "Fecha de fin: $formattedDate")
            }
            // Puedes añadir un Switch para indicar si hay fecha de fin o no.

            // --- Switch de Activo ---
            Row(
                Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Horario activo")
                Switch(
                    checked = scheduleState.isActive,
                    onCheckedChange = viewModel::updateIsActive
                )
            }


            Spacer(modifier = Modifier.weight(1f)) // Empuja el botón hacia abajo

            Button(
                onClick = {
                    val newScheduleObject = viewModel.validateAndCreateSchedule() // Esta función debe estar en ScheduleFormViewModel
                    newScheduleObject?.let { scheduleToReturn ->
                        // Devolver el resultado a AddMedicationScreen
                        navController.previousBackStackEntry
                            ?.savedStateHandle
                            ?.set(AddMedicationViewModel.NEW_SCHEDULE_RESULT_KEY, scheduleToReturn) // Usa la clave constante
                        navController.popBackStack()
                    }
                    // Si newScheduleObject es null, el ViewModel debería haber actualizado su UIState con errores
                    // y el LaunchedEffect los mostrará en el Snackbar.
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Añadir Horario a la Lista")
            }
        }
    }

    if (showTimePicker) {
        TimePickerDialog(
            initialTime = scheduleState.time,
            onDismissRequest = { showTimePicker = false },
            onTimeSelected = { newTime ->
                viewModel.updateTime(newTime) // viewModel es ScheduleFormViewModel
                showTimePicker = false
            }
        )
    }
    if (showStartDatePicker) {
        DatePickerDialog(
            initialDate = scheduleState.startDate,
            onDismissRequest = { showStartDatePicker = false },
            onDateSelected = { newDate ->
                viewModel.updateStartDate(newDate)
                showStartDatePicker = false
            }
        )
    }
    if (showEndDatePicker) {
        DatePickerDialog(
            // Podrías querer una lógica más inteligente para la fecha inicial aquí
            initialDate = scheduleState.endDate ?: scheduleState.startDate.plusDays(1),
            onDismissRequest = { showEndDatePicker = false },
            onDateSelected = { newDate ->
                viewModel.updateEndDate(newDate)
                showEndDatePicker = false
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DaySelector(
    selectedDays: List<DayOfWeek>, // Mejor usar Set<DayOfWeek> para eficiencia en 'contains'
    onDayToggled: (DayOfWeek) -> Unit, // Cambiado el nombre para claridad
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        DayOfWeek.entries.forEach { day ->
            val isSelected = selectedDays.contains(day)
            FilterChip(
                selected = isSelected,
                onClick = { onDayToggled(day) },
                label = { Text(day.getDisplayName(TextStyle.SHORT, Locale.getDefault())) }
            )
        }
    }
}
