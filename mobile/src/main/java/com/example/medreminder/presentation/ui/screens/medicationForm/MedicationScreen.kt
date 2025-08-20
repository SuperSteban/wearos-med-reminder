package com.example.medreminder.presentation.ui.screens.medicationForm

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.outlined.MedicalServices
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.medreminder.domain.model.Medication
import com.example.medreminder.domain.model.MedicationForm
import com.example.medreminder.presentation.ui.screens.home.components.MedActionComponent
import com.example.medreminder.presentation.ui.screens.medicationForm.viewModel.MedicationListViewModel


private val MedicationForm.displayName: String
    get() = when(this) {
        MedicationForm.PILL -> "Pastilla"
        MedicationForm.CAPSULE -> "Cápsula"
        MedicationForm.LIQUID -> "Líquido"
        MedicationForm.INJECTION -> "Inyección"
        MedicationForm.CREAM -> "Crema"
        MedicationForm.DROPS -> "Gotas"
        MedicationForm.INHALER -> "Inhalador"
        MedicationForm.OTHER -> "Otro"
    }

@Composable
fun MedicationsScreen(
    navController: NavHostController,
    viewModel: MedicationListViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadMedications()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Medicamentos",
                    style = MaterialTheme.typography.headlineMedium
                )
                Text(
                    text = "${uiState.medications.size} medicamentos",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            FloatingActionButton(
                onClick = { navController.navigate("add_medication") }
            ) {
                Icon(Icons.Default.Add, contentDescription = "Agregar medicamento")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Lista de medicamentos
        when {
            uiState.isLoading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            uiState.error != null -> {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.errorContainer
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = "Error",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onErrorContainer
                        )
                        Text(
                            text = uiState.error!!,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onErrorContainer
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(
                            onClick = { viewModel.loadMedications() }
                        ) {
                            Text("Reintentar")
                        }
                    }
                }
            }

            uiState.medications.isEmpty() -> {
                EmptyMedicationsList(
                    onAddClick = { navController.navigate("add_medication") }
                )
            }

            else -> {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(uiState.medications) { medication ->
                        MedicationItem(
                            medication = medication,
                            onToggleActive = { viewModel.toggleMedicationActive(medication) },
                            onDelete = { viewModel.deleteMedication(medication.id) },
                            onTaken = { /* Implementar lógica de medicamento tomado */ },
                            onPostpone = { /* Implementar lógica de posponer */ },
                            onSkip = { /* Implementar lógica de saltar */ }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun MedicationItem(
    medication: Medication,
    onToggleActive: () -> Unit,
    onDelete: () -> Unit,
    onTaken: () -> Unit,
    onPostpone: () -> Unit,
    onSkip: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = if (medication.isActive)
                MaterialTheme.colorScheme.surface
            else
                MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = medication.name,
                        style = MaterialTheme.typography.titleMedium,
                        color = if (medication.isActive)
                            MaterialTheme.colorScheme.onSurface
                        else
                            MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "${medication.dosage} • ${medication.form.displayName}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "Horarios: ${medication.schedule.size}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Row {
                    Switch(
                        checked = medication.isActive,
                        onCheckedChange = { onToggleActive() }
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    IconButton(onClick = onDelete) {
                        Icon(
                            Icons.Default.Delete,
                            contentDescription = "Eliminar",
                            tint = MaterialTheme.colorScheme.error
                        )
                    }
                }
            }

            if (medication.isActive) {
                Spacer(modifier = Modifier.height(12.dp))
                MedActionComponent(
                    onTaken = onTaken,
                    onPostpone = onPostpone,
                    onSkip = onSkip
                )
            }
        }
    }
}

@Composable
fun EmptyMedicationsList(
    onAddClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Outlined.MedicalServices,
            contentDescription = null,
            modifier = Modifier.size(64.dp),
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "No hay medicamentos",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = "Agrega tu primer medicamento para comenzar",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = onAddClick
        ) {
            Icon(Icons.Default.Add, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Agregar Medicamento")
        }
    }
}