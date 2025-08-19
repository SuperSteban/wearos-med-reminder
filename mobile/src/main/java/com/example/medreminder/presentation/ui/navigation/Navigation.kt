package com.example.medreminder.presentation.ui.navigation

import android.net.http.SslCertificate.saveState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MedicalServices
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Watch
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.MedicalServices
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material.icons.outlined.Watch
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.medreminder.presentation.ui.screens.conection.ConectionScreen
import com.example.medreminder.presentation.ui.screens.home.HomeScreen
import com.example.medreminder.presentation.ui.screens.medReminderForm.MedReminderFormScreen
import com.example.medreminder.presentation.ui.screens.medicationForm.MedicationScreen

// Rutas de navegación
object MedAppRoutes {
    const val HOME = "home"
    const val MEDICATIONS = "medications"
    const val SCHEDULE = "schedule"

    const val CONECCTION = "conecction"
}

data class NavigationItem(
    val route: String,
    val title: String,
    val icon: ImageVector,
    val selectedIcon: ImageVector = icon
)

val navigationItems = listOf(
    NavigationItem(
        route = MedAppRoutes.HOME,
        title = "Inicio",
        icon = Icons.Outlined.Home,
        selectedIcon = Icons.Filled.Home
    ),
    NavigationItem(
        route = MedAppRoutes.MEDICATIONS,
        title = "Medicamentos",
        icon = Icons.Outlined.MedicalServices,
        selectedIcon = Icons.Filled.MedicalServices
    ),
    NavigationItem(
        route = MedAppRoutes.SCHEDULE,
        title = "Horarios",
        icon = Icons.Outlined.Schedule,
        selectedIcon = Icons.Filled.Schedule
    ),
    NavigationItem(
        route = MedAppRoutes.CONECCTION,
        title = "Conection",
        icon = Icons.Outlined.Watch,
        selectedIcon = Icons.Filled.Watch
    ),

)

@Composable
fun MedAppNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = MedAppRoutes.HOME,
        modifier = modifier
    ) {
        composable(MedAppRoutes.HOME) {
            HomeScreen(navController = navController)
        }

        composable(MedAppRoutes.MEDICATIONS) {
            MedicationScreen(navController = navController)
        }

        composable(MedAppRoutes.SCHEDULE) {
            MedReminderFormScreen(navController = navController)
        }
        composable(MedAppRoutes.CONECCTION) {
            ConectionScreen(navController = navController)
        }


    }
}
@Composable
fun MedAppBottomNavigation(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onSurface
    ) {
        navigationItems.forEach { item ->
            val isSelected = currentRoute == item.route

            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = if (isSelected) item.selectedIcon else item.icon,
                        contentDescription = item.title
                    )
                },
                label = {
                    Text(
                        text = item.title,
                        style = MaterialTheme.typography.labelSmall
                    )
                },
                selected = isSelected,
                onClick = {
                    if (currentRoute != item.route) {
                        navController.navigate(item.route) {
                            // Pop up to the start destination to avoid building up a large stack
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            // Avoid multiple copies of the same destination
                            launchSingleTop = true
                            // Restore state when reselecting
                            restoreState = true
                        }
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    indicatorColor = MaterialTheme.colorScheme.primaryContainer,
                    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                )
            )
        }
    }
}
@Composable
fun MedApp() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            MedAppBottomNavigation(navController = navController)
        }
    ) { paddingValues ->
        MedAppNavGraph(
            navController = navController,
            modifier = Modifier.padding(paddingValues)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MedAppNavigationPreview() {
    MaterialTheme {
        MedApp()
    }
}

@Preview(showBackground = true, name = "Medications Screen")
@Composable
fun MedicationsScreenPreview() {
    MaterialTheme {
        MedicationScreen(navController = rememberNavController())
    }
}