package com.example.medreminder.domain.model


data class Medication(
    val id: Int,
    val name: String,
    val dosage: String,
    val isActive: Boolean,
    val schedule: List<MedSchedules>,
    val form: MedicationForm,
)
