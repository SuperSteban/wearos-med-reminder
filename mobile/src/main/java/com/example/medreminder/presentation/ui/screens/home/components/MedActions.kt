package com.example.medreminder.presentation.ui.screens.home.components

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

/*@Composable
fun medActionComponent(
    onTaken:()->Unit,
    onPostpone:()->Unit,
    onSkip:()-> Unit,
    ) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        Button(onTaken) { }
        Button(onPostpone) { }
        Button(onSkip) { }


    }

}
*/
@Composable
fun MedActionComponent(
    onTaken: () -> Unit,
    onPostpone: () -> Unit,
    onSkip: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(10.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        FilledIconButton(
            onClick = onTaken,
            modifier = Modifier.weight(1f),

        ) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = null,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))

            Text("Tomado")
        }

        Button(
            onClick = onPostpone,
            modifier = Modifier.weight(1f),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.secondary
            )
        ) {
            Icon(
                imageVector = Icons.Default.Schedule,
                contentDescription = null,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text("Posponer")
        }

        Button(
            onClick = onSkip,
            modifier = Modifier.weight(1f),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.error
            )
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = null,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text("Saltar")
        }
    }
}
@Composable
fun FilledButtonExample(action: ()->Unit, text: String) {
    Button(onClick = { action }) {
        Text("Filled")
    }
}

@Preview(showSystemUi = false, device = "id:pixel_8_pro", name = "Container")
@Preview(showSystemUi = true, device = "id:pixel_8_pro", name = "Pixel 8")
@Preview(showSystemUi = true, device = "id:Nexus One", name = "nexus")
@Composable
fun MedActionComponentPreview() {
    MaterialTheme {
        MedActionComponent(
            onTaken = { /* Acción de tomado */ },
            onPostpone = { /* Acción de posponer */ },
            onSkip = { /* Acción de saltar */ }
        )
    }
}
