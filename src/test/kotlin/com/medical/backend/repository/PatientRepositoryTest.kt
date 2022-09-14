package com.medical.backend.repository

import com.medical.backend.model.Patient
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import org.springframework.dao.DuplicateKeyException

@DataMongoTest

class PatientRepositoryTest {
    @Autowired
    lateinit var patientRepository: PatientRepository

    @BeforeEach
    fun tearDown() {
        patientRepository.deleteAll().subscribe()
    }

    @Test
    fun `should find book for given id `() {

        val patient = Patient(patientId = "1", patientFirstName = "piyush", patientLastname = "kumar", userName = "abc", mobileNumber = "123", email = "abc",
        gender = "male", dob = "01012001", password = "abc123", address = "xyz")

        patientRepository.save(patient).block()

        val actualSaveApplication = patientRepository.findById(
            "1"
        ).block()

        if (actualSaveApplication != null) {
            actualSaveApplication shouldBe patient
        }
    }

    @Test
    fun `should not store duplicate application with same id`() {

        val patient1 = Patient(patientId = "1", patientFirstName = "piyush", patientLastname = "kumar", userName = "abc", mobileNumber = "123", email = "abc",
            gender = "male", dob = "01012001", password = "abc123", address = "xyz")

        val patient2 = Patient(patientId = "1", patientFirstName = "kumar", patientLastname = "piyush", userName = "abc", mobileNumber = "123", email = "abc",
            gender = "male", dob = "01012001", password = "abc123", address = "xyz")

        patientRepository.insert(patient1).block()

        assertThrows<DuplicateKeyException> { patientRepository.insert(patient2).block() }

    }
}