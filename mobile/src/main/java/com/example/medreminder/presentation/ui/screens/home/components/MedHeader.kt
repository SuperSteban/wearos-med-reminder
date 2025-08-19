package com.example.medreminder.presentation.ui.screens.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Android
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.filled.Mail
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun medHeader(){
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .background(color = MaterialTheme.colorScheme.primaryContainer)
                .height(IntrinsicSize.Min),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Default.Android,
                tint = MaterialTheme.colorScheme.secondary,
                contentDescription = "None",
                modifier = Modifier.size(64.dp)
            )
        }
        Spacer(modifier = Modifier.size(8.dp))
        Column(
            modifier =  Modifier
                .padding(8.dp)
                .fillMaxWidth(.8f)
                .height(IntrinsicSize.Min),

            ) {
            Text(
                "Paracetamol",
                fontSize = 28.sp
            )
            Text(
                "Paracetamol"
            )
            BadgedBox(
                badge = {
                    Badge ()
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.Mail,
                    contentDescription = "Email"
                )
            }

        }
        Column(
            modifier = Modifier.fillMaxWidth()
                .padding(10.dp)
                .height(IntrinsicSize.Min),
            verticalArrangement = Arrangement.Top,
        ) {
            BadgedBox(
                badge = {}
            ) {
                Icon(
                    modifier = Modifier.size(16.dp),
                    imageVector = Icons.Filled.Circle,
                    contentDescription = "Mark Point",
                )
            }
        }
    }
}