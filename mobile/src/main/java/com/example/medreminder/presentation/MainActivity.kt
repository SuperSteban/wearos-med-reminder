package com.example.medreminder.presentation

import android.os.Bundle
import androidx.activity.compose.setContent

import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.MaterialTheme

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.medreminder.presentation.ui.common.components.header.Header
import com.example.medreminder.presentation.ui.navigation.MedApp
import com.example.medreminder.presentation.ui.navigation.MedAppBottomNavigation


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            MaterialTheme {
                MedApp()
            }
        }
    }

}


@Preview(
    showSystemUi = true, device = "id:pixel_8_pro"
)
@Composable
fun SplashPreview() {

    Spacer(modifier = Modifier.width(8.dp))

    MedApp()


}