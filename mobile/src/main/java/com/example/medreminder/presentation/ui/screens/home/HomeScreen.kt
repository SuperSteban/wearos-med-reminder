package com.example.medreminder.presentation.ui.screens.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.medreminder.domain.model.MedSchedules
import com.example.medreminder.domain.model.Medication
import com.example.medreminder.domain.model.MedicationForm
import com.example.medreminder.presentation.ui.common.components.header.Header
import com.example.medreminder.presentation.ui.screens.home.components.MedicationComponent
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalTime


val medicationsSetExample = listOf<Medication>(
    Medication(
        1,
        name = "Insulina Lantus",
        dosage = "20 unidades",
        form = MedicationForm.INJECTION,
        isActive = true,
        schedule = listOf(
            MedSchedules(
                id = "sch_003_1",
                time = LocalTime.of(7, 0),
                daysOfWeek = DayOfWeek.values().toList(),
                startDate = LocalDate.now().minusDays(60),
                endDate = null,
                isActive = true
            ), MedSchedules(
                id = "sch_003_2",
                time = LocalTime.of(19, 0),
                daysOfWeek = DayOfWeek.values().toList(),
                startDate = LocalDate.now().minusDays(60),
                endDate = null,
                isActive = true
            )
        ),
    ),
    Medication(
        id = 1,
        name = "Insulina Lantus",
        dosage = "20 unidades",
        form = MedicationForm.INJECTION,
        isActive = true,
        schedule = listOf(
            MedSchedules(
                id = "sch_003_1",
                time = LocalTime.of(7, 0),
                daysOfWeek = DayOfWeek.values().toList(),
                startDate = LocalDate.now().minusDays(60),
                endDate = null,
                isActive = true
            ), MedSchedules(
                id = "sch_003_2",
                time = LocalTime.of(19, 0),
                daysOfWeek = DayOfWeek.values().toList(),
                startDate = LocalDate.now().minusDays(60),
                endDate = null,
                isActive = true
            )
        ),
    ),
    Medication(
        1,
        name = "Insulina Lantus",
        dosage = "20 unidades",
        form = MedicationForm.INJECTION,
        isActive = true,
        schedule = listOf(
            MedSchedules(
                id = "sch_003_1",
                time = LocalTime.of(7, 0),
                daysOfWeek = DayOfWeek.values().toList(),
                startDate = LocalDate.now().minusDays(60),
                endDate = null,
                isActive = true
            ),
            MedSchedules(
                id = "sch_003_2",
                time = LocalTime.of(19, 0),
                daysOfWeek = DayOfWeek.values().toList(),
                startDate = LocalDate.now().minusDays(60),
                endDate = null,
                isActive = true
            )
        ),
    ),
    Medication(
        1,
        name = "Insulina Lantus",
        dosage = "20 unidades",
        form = MedicationForm.INJECTION,
        isActive = true,
        schedule = listOf(
            MedSchedules(
                id = "sch_003_1",
                time = LocalTime.of(7, 0),
                daysOfWeek = DayOfWeek.values().toList(),
                startDate = LocalDate.now().minusDays(60),
                endDate = null,
                isActive = true
            ),
            MedSchedules(
                id = "sch_003_2",
                time = LocalTime.of(19, 0),
                daysOfWeek = DayOfWeek.values().toList(),
                startDate = LocalDate.now().minusDays(60),
                endDate = null,
                isActive = true
            )
        ),
    )
)

@Composable
fun HomeScreen(
    //medications: List<Medication>?,
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    Header(
        title = "Bienvenido, Jorge Corral",
        icon = Icons.Default.AccountCircle
    )
    Box(
        modifier = modifier,
    ) {
        LazyColumn(Modifier.fillMaxWidth(), content = {
            itemsIndexed(medicationsSetExample, itemContent = { index, item ->

                MedicationComponent(
                    time = TODO(),
                    subTime = TODO(),
                    onTaken = TODO(),
                    onPostpone = TODO(),
                    onSkip = TODO(),
                    modifier = TODO()
                )

            })
        })

    }
}

@Preview(showSystemUi = true)
@Composable
fun homePreviewList() {
    HomeScreen(
        navController = rememberNavController()
    )
}
