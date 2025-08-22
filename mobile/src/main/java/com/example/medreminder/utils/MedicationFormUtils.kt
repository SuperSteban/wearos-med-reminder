package com.example.medreminder.utils

import com.example.medreminder.domain.model.MedicationForm



val MedicationForm.displayName: String
    get() = when(this) {
        MedicationForm.PILL -> "Pastilla"
        MedicationForm.CAPSULE -> "Cápsula"
        MedicationForm.LIQUID -> "Líquido"
        MedicationForm.INJECTION -> "Inyección"
        MedicationForm.CREAM -> "Crema"
        MedicationForm.DROPS -> "Gotas"
        MedicationForm.INHALER -> "Inhalador"
        MedicationForm.OTHER -> "Otro"
    }
