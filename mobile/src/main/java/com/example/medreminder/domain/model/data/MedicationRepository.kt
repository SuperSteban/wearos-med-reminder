package com.example.medreminder.domain.model.data

import com.example.medreminder.domain.model.Medication
import kotlinx.coroutines.flow.Flow

interface MedicationRepository {
    suspend fun getAllMedications(): List<Medication>
    suspend fun getMedicationById(id: Int): Medication?
    suspend fun insertMedication(medication: Medication): Int
    suspend fun updateMedication(medication: Medication)
    suspend fun deleteMedication(id: Int)
}

class MedicationRepositoryImpl : MedicationRepository {
    // Simulación de base de datos en memoria
    private val medications = mutableListOf<Medication>()
    private var nextId = 1

    override suspend fun getAllMedications(): List<Medication> {
        return medications.toList()
    }

    override suspend fun getMedicationById(id: Int): Medication? {
        return medications.find { it.id == id }
    }

    override suspend fun insertMedication(medication: Medication): Int {
        val newMedication = medication.copy(id = nextId++)
        medications.add(newMedication)
        return newMedication.id
    }

    override suspend fun updateMedication(medication: Medication) {
        val index = medications.indexOfFirst { it.id == medication.id }
        if (index != -1) {
            medications[index] = medication
        }
    }

    override suspend fun deleteMedication(id: Int) {
        medications.removeAll { it.id == id }
    }
}