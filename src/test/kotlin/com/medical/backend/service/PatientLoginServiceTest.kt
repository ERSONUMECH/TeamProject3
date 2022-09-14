package com.medical.backend.service

import com.medical.backend.model.Patient
import com.medical.backend.repository.PatientRepository
import io.kotlintest.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.test.StepVerifier

class PatientLoginServiceTest {
    // mocking the repository layer response

    val patient1 = Patient(patientId = "1", patientFirstName = "abc", patientLastname = "abc", userName = "abd", mobileNumber = "12345", email = "abv", gender = "M", dob = "23121996", password = "32az", address = "asx")

    val patient2 = Patient(patientId = "2", patientFirstName = "bac", patientLastname = "adc", userName = "cbd", mobileNumber = "12045", email = "abv", gender = "M", dob = "23121996", password = "32az", address = "asv")


    private val bookRepository = mockk<PatientRepository>{

        every {
            findAll()
        } returns Flux.just(patient1,patient2)

        every {
            findById("1")
        } returns Mono.just(patient1)
    }

    private val patientService = PatientService(bookRepository)


    @Test
    fun `should return books when find all method is called`() {

        val firstPatient =  patientService.findAll().blockFirst()
        val secondPatient = patientService.findAll().blockLast()

        if (firstPatient != null) {
            firstPatient shouldBe  patient1
        }
        if (secondPatient != null) {
            secondPatient shouldBe patient2
        }
    }

    @Test
    fun `should expect on complete call post all the books are retrieved`() {

        //StepVerifier takes care of subscribing

        StepVerifier.create( patientService.findAll()).expectSubscription().expectNext(patient1).expectNext(patient2).verifyComplete()
        StepVerifier.create( patientService.findAll()).expectNextCount(2).verifyComplete()
    }

    @Test
    fun `should find the list of books on the basis of the id`() {

        val result = patientService.findOnePatient("1")

        result shouldBe patient1
    }

}