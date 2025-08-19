package com.example.medreminder.presentation.ui.screens.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Composable
fun medBody(
    time: String?,
    subTime: String?,

) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.primaryContainer),

    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                Modifier.fillMaxWidth()
            ) {
                Text(
                    modifier =  Modifier.fillMaxWidth(),
                    text = "Próxima dosis: $time PM",
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 20.sp,

                    )
            }
            Row(
                Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "En $subTime • Lun, Mie, Vie",
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 20.sp,

                    )
            }
        }
    }
}

@Preview(showSystemUi = false, device = "id:pixel_8_pro", name = "Container")
@Preview(showSystemUi = true, device = "id:pixel_8_pro", name = "Pixel 8")
@Preview(showSystemUi = true, device = "id:Nexus One", name = "nexus")
@Composable
fun medBodyPreview() {
    var timeL = LocalTime.of(7, 0)
    var subTimeToTake = LocalTime.now().minusHours(7).format(DateTimeFormatter.ofPattern("HH:mm:ss"))
    medBody(timeL.toString(), subTimeToTake.toString())
}