package com.example.medreminder.domain.model.data

import com.example.medreminder.domain.model.MedSchedules

interface ScheduleRepository {
    suspend fun getAllSchedules(): List<MedSchedules>
    suspend fun getScheduleById(id: Int): MedSchedules?
    suspend fun insertSchedule(schedule: MedSchedules): Int
    suspend fun updateSchedule(schedule: MedSchedules)
    suspend fun deleteSchedule(id: Int)
}

class  ScheduleRepositoryImpl: ScheduleRepository {
    override suspend fun getAllSchedules(): List<MedSchedules> {
        TODO("Not yet implemented")
    }

    override suspend fun getScheduleById(id: Int): MedSchedules? {
        TODO("Not yet implemented")
    }

    override suspend fun insertSchedule(schedule: MedSchedules): Int {
        TODO("Not yet implemented")
    }

    override suspend fun updateSchedule(schedule: MedSchedules) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteSchedule(id: Int) {
        TODO("Not yet implemented")
    }

}

