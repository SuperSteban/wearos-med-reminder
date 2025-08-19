package com.example.medreminder.utils

import com.example.medreminder.domain.model.MedSchedules
import java.time.LocalDate
import java.time.LocalDateTime

object TimeUtils {
    fun shouldScheduleRun(schedule: MedSchedules, date: LocalDate): Boolean {
        if (date.isBefore(schedule.startDate)) return false
        if (schedule.endDate != null && date.isAfter(schedule.endDate)) return false

        return schedule.daysOfWeek.contains(date.dayOfWeek)
    }

    fun isOverdue(scheduledTime: LocalDateTime): Boolean {
        val now = LocalDateTime.now()
        return now.isAfter(scheduledTime.plusMinutes(30)) // 30 min grace period
    }

    fun canTakeNow(scheduledTime: LocalDateTime): Boolean {
        val now = LocalDateTime.now()
        val windowStart = scheduledTime.minusMinutes(15)
        val windowEnd = scheduledTime.plusHours(2)

        return now.isAfter(windowStart) && now.isBefore(windowEnd)
    }

    /*fun getTimeUntilNext(scheduledTime: LocalDateTime): Duration {
        val now = LocalDateTime.now()
        return Duration.between(now, scheduledTime)
    }

    fun formatTimeRemaining(duration: Duration): String {
        val hours = duration.toHours()
        val minutes = duration.toMinutes() % 60

        return when {
            hours > 0 -> "${hours}h ${minutes}m"
            minutes > 0 -> "${minutes}m"
            else -> "Ahora"
        }*/
    }
