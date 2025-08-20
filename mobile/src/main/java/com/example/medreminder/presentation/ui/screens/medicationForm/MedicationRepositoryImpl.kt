package com.example.medreminder.presentation.ui.screens.medicationForm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.medreminder.domain.model.data.MedicationRepository
import com.example.medreminder.presentation.ui.screens.medicationForm.viewModel.MedicationListViewModel


class MedicationListViewModelFactory(
    private val repository: MedicationRepository // La factory recibe la dependencia
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MedicationListViewModel::class.java)) {
            // Pasa la dependencia al constructor del ViewModel
            return MedicationListViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}