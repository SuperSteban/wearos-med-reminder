package com.example.medreminder.presentation.ui.screens.medReminderForm

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.medreminder.domain.model.MedSchedules
import com.example.medreminder.presentation.ui.screens.medicationForm.viewModel.AddMedicationViewModel
import java.time.format.DateTimeFormatter

@Composable
fun ScheduleListScreen(
    navController: NavHostController,
    viewModel: AddMedicationViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val schedules = uiState.schedules

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Horarios",
                style = MaterialTheme.typography.headlineSmall
            )
            FloatingActionButton(
                onClick = { navController.navigate("schedule_form") },
                modifier = Modifier.size(48.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Agregar horario")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (schedules.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Aún no hay horarios",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                itemsIndexed(schedules) { index, schedule ->
                    ScheduleItem(
                        schedule = schedule,
                        // Corregido: Llamar a la función removeSchedule del ViewModel
                        onDelete = { viewModel.removeSchedule(index) }
                    )
                }
            }
        }
    }
}

@Composable
fun ScheduleItem(
    schedule: MedSchedules,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = schedule.time.format(DateTimeFormatter.ofPattern("HH:mm")),
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = schedule.daysOfWeek.joinToString {
                        it.getDisplayName(java.time.format.TextStyle.SHORT, java.util.Locale.getDefault())
                    },
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            IconButton(onClick = onDelete) {
                Icon(
                    Icons.Default.Delete,
                    contentDescription = "Eliminar",
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}