package com.example.medreminder.presentation.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MedicalServices
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Watch
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.MedicalServices
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material.icons.outlined.Watch
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.medreminder.domain.model.data.MedicationRepository
import com.example.medreminder.domain.model.data.MedicationRepositoryImpl
import com.example.medreminder.presentation.ui.screens.conection.ConectionScreen
import com.example.medreminder.presentation.ui.screens.home.HomeScreen
import com.example.medreminder.presentation.ui.screens.medReminderForm.ScheduleFormScreen
import com.example.medreminder.presentation.ui.screens.medReminderForm.ScheduleListScreen
import com.example.medreminder.presentation.ui.screens.medicationForm.AddMedicationScreen
import com.example.medreminder.presentation.ui.screens.medicationForm.MedicationListViewModelFactory
import com.example.medreminder.presentation.ui.screens.medicationForm.MedicationsScreen
import com.example.medreminder.presentation.ui.screens.medicationForm.viewModel.AddMedicationViewModel

// Rutas de navegación
object MedAppRoutes {
    const val ADD_SCHEDULE = "add_schedule"
    const val HOME = "home"
    const val MEDICATIONS = "medications"
    const val SCHEDULE = "schedule"

    const val CONNECTION = "connection"

    const val ADD_MEDICATION = "add_medication"
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
        route = MedAppRoutes.CONNECTION,
        title = "Conexion",
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
            MedicationsScreen(navController = navController)
        }
        composable(MedAppRoutes.ADD_MEDICATION) {
            val medicationRepository: MedicationRepository = MedicationRepositoryImpl()
            val factory = MedicationListViewModelFactory(medicationRepository)
            val addMedicationViewModel: AddMedicationViewModel = viewModel(factory = factory)
            AddMedicationScreen(navController = navController,
               viewModel = addMedicationViewModel
            )
        }
        composable(MedAppRoutes.SCHEDULE) {
            ScheduleListScreen(
                navController,
                viewModel = viewModel<AddMedicationViewModel>()
            )
        }
        composable(MedAppRoutes.ADD_SCHEDULE) {
            ScheduleFormScreen(
                navController
            )
        }

        composable(MedAppRoutes.CONNECTION) {
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
        MedicationsScreen(navController = rememberNavController())
    }
}