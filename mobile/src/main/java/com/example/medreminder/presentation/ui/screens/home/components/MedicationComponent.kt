package com.example.medreminder.presentation.ui.screens.home.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Composable
fun MedicationComponent(
    time: String,
    subTime: String,
    onTaken: () -> Unit,
    onPostpone: () -> Unit,
    onSkip: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card (
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)

    ){
        medHeader()
        medBody(time.toString(), subTime.toString())
        MedActionComponent(
            onTaken = onTaken,
            onPostpone = onPostpone,
            onSkip = onSkip
        )
    }
}

@Preview(showSystemUi = false, device = "id:pixel_8_pro", name = "Container")
@Preview(showSystemUi = true, device = "id:pixel_8_pro", name = "Pixel 8")
@Preview(showSystemUi = true, device = "id:Nexus One", name = "nexus")
@Composable
private fun MedicationPreview() {
    var timeL = LocalTime.of(7, 0)
    var subTimeToTake = LocalTime.now().minusHours(7).format(DateTimeFormatter.ofPattern("HH:mm:ss"))
    MaterialTheme {
        MedicationComponent(
            timeL.toString(),
            subTimeToTake.toString(),
            onTaken = { },
            onPostpone = { },
            onSkip = { },
        )
    }
}


