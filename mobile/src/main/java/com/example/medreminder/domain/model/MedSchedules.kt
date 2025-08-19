package com.example.medreminder.domain.model

import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalTime

data class MedSchedules(
    val id: String,
    val time: LocalTime,
    val daysOfWeek: List<DayOfWeek>,
    val startDate: LocalDate,
    val endDate: LocalDate?,
    val isActive: Boolean
)
