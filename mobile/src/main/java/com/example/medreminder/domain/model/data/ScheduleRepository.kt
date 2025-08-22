package com.example.medreminder.domain.model.data

import com.example.medreminder.domain.model.MedSchedules

interface ScheduleRepository {
    suspend fun getAllSchedules(): List<MedSchedules>
    suspend fun getScheduleById(id: Int): MedSchedules?
    suspend fun insertSchedule(schedule: MedSchedules): Int
    suspend fun updateSchedule(schedule: MedSchedules)
    suspend fun deleteSchedule(id: Int)

    suspend fun loadSchedulesForMedication(medicationId: Int): List<MedSchedules>

}

class  ScheduleRepositoryImpl: ScheduleRepository {
    private val scheduleList = mutableListOf<MedSchedules>(

    )

    // TO Simulate an ID autoincrement
    private var nextId = 1

    override suspend fun loadSchedulesForMedication(medicationId: Int): List<MedSchedules> {

        return  scheduleList.filter { it.medicationId == medicationId }
    }

    override suspend fun getAllSchedules(): List<MedSchedules> {
        return  scheduleList.toList()
    }

    override suspend fun getScheduleById(id: Int): MedSchedules? {
        return  scheduleList.find { it.id == id }
    }

    override suspend fun insertSchedule(schedule: MedSchedules): Int {
        // Makes a copy and adds 1
        val newSchedule = schedule.copy(nextId ++)
        scheduleList.add(newSchedule)
        return  newSchedule.id
    }

    override suspend fun updateSchedule(schedule: MedSchedules) {
        val index = scheduleList.indexOfFirst { it.id == schedule.id }
        if (index != -1) {
            scheduleList[index] = schedule
        }
    }

    override suspend fun deleteSchedule(id: Int)  {
        scheduleList.removeAll { it.id == id }
    }



}

