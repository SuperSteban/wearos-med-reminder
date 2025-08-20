package com.example.medreminder.presentation.ui.screens.medicationForm

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.medreminder.domain.model.MedSchedules
import com.example.medreminder.domain.model.MedicationForm
import com.example.medreminder.presentation.ui.screens.medicationForm.viewModel.AddMedicationViewModel
import com.example.medreminder.presentation.ui.screens.medicationForm.viewModel.MedicationListViewModel
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddMedicationScreen(
    navController: NavHostController,
    viewModel: AddMedicationViewModel,
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState.isSuccess) {
        if (uiState.isSuccess) {
            navController.popBackStack()
            viewModel.resetState() // Restablecer el estado para futuros usos
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Agregar Medicamento") },
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
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Campos de texto para nombre y dosis
            OutlinedTextField(
                value = uiState.name,
                onValueChange = viewModel::updateName,
                label = { Text("Nombre del medicamento") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = uiState.dosage,
                onValueChange = viewModel::updateDosage,
                label = { Text("Dosis (ej. 500 mg)") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Selector de forma del medicamento
            MedicationFormDropdown(
                selectedForm = uiState.selectedForm,
                onFormSelected = viewModel::updateForm
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Sección de Horarios
            ScheduleSection(
                schedules = uiState.schedules,
                onAddSchedule = { navController.navigate("add_schedule") },
                onRemoveSchedule = viewModel::removeSchedule
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Mostrar errores si existen
            if (uiState.error != null) {
                Text(
                    text = uiState.error!!,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
            }

            // Botón de guardar
            Button(
                onClick = viewModel::saveMedication,
                modifier = Modifier.fillMaxWidth(),
                enabled = !uiState.isLoading
            ) {
                if (uiState.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                } else {
                    Text("Guardar Medicamento")
                }
            }
        }
    }
}

// Componente para la sección de horarios
@Composable
fun ScheduleSection(
    schedules: List<MedSchedules>,
    onAddSchedule: () -> Unit,
    onRemoveSchedule: (Int) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Horarios",
                style = MaterialTheme.typography.titleMedium
            )
            IconButton(onClick = onAddSchedule) {
                Icon(Icons.Default.Add, contentDescription = "Agregar horario")
            }
        }
        if (schedules.isEmpty()) {
            Text(
                text = "No hay horarios agregados.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        } else {
            LazyColumn(
                modifier = Modifier.heightIn(max = 200.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(items = schedules.toMutableList(), key = { it.time }) { schedule ->
                    val index = schedules.indexOf(schedule)
                    ScheduleItem(
                        schedule = schedule,
                        onRemove = { onRemoveSchedule(index) }
                    )
                }
            }
        }
    }
}

// Componente para mostrar un solo horario
@Composable
fun ScheduleItem(
    schedule: MedSchedules,
    onRemove: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = schedule.time.format(DateTimeFormatter.ofPattern("hh:mm a")),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = schedule.daysOfWeek.joinToString { it.getDisplayName(java.time.format.TextStyle.SHORT, java.util.Locale.getDefault()) },
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.weight(2f)
        )
        IconButton(onClick = onRemove) {
            Icon(Icons.Default.Delete, contentDescription = "Eliminar horario", tint = MaterialTheme.colorScheme.error)
        }
    }
}

// Reutilizamos el dropdown del ejemplo anterior
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MedicationFormDropdown(
    selectedForm: MedicationForm,
    onFormSelected: (MedicationForm) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            OutlinedTextField(
                readOnly = true,
                value = selectedForm.displayName,
                onValueChange = { },
                label = { Text("Forma") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor()
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                MedicationForm.entries.forEach { form ->
                    DropdownMenuItem(
                        text = { Text(form.displayName) },
                        onClick = {
                            onFormSelected(form)
                            expanded = false
                        },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                    )
                }
            }
        }
    }
}

private val MedicationForm.displayName: String
    get() = when(this) {
        MedicationForm.PILL -> "Pildora"
        MedicationForm.LIQUID -> "Liquido"
        MedicationForm.INJECTION -> "Injeccion"
        MedicationForm.CREAM -> "Crema"
        MedicationForm.INHALER -> "Inalador"
        MedicationForm.DROPS -> "Gotas"
        MedicationForm.OTHER -> "otro"
        MedicationForm.CAPSULE -> "Capsulas"
    }