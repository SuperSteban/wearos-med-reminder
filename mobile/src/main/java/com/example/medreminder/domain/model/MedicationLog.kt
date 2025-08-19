package com.example.medreminder.domain.model

import java.time.LocalDateTime

data class MedicationLog(
    val id: String,
    val medication: Medication,
    val schedule: MedSchedules,
    val scheduledTime: LocalDateTime,
    val actualTime: LocalDateTime?,
    val status: LogStatus,
    val notes: String?
)
