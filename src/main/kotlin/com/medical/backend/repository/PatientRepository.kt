package com.medical.backend.repository

import com.medical.backend.model.Patient
import org.springframework.data.mongodb.repository.ReactiveMongoRepository

interface PatientRepository : ReactiveMongoRepository<Patient, String>