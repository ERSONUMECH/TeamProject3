package com.medical.backend.controller

import com.medical.backend.model.Patient
import com.medical.backend.service.BookAppointmentService
import com.medical.backend.service.PatientService
import io.kotlintest.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.returnResult
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono


@WebFluxTest(PatientController::class)
@AutoConfigureWebTestClient

class PatientControllerTest {
    @Autowired
    lateinit var client : WebTestClient

    @Autowired
    lateinit var patientService: PatientService

    @TestConfiguration
    class ControllerTestConfig {
        @Bean
        fun patientService() = mockk<PatientService>()
    }
    @Test
    fun Test1() {
        val patient1 = Patient(
            patientId = "1",
            patientFirstName = "abc",
            patientLastname = "xyz",
            userName = "dk",
            mobileNumber = "12345",
            email = "abc",
            gender = "m",
            dob = "26121996",
            password = "abx1",
            address = "xzy"
        )
        val expectedResult = mapOf(
            "patientId" to "1",
            "patientFirstName" to "abc",
            "patientLastName" to "xyz",
            "userName" to "dk",
            "mobileNumber" to "12345",
            "email" to "abc",
            "gender" to "m",
            "dob" to "26121996",
            "password" to "abx1",
            "address" to "xzy"
        )

        every {
            patientService.findAll()
        } returns Flux.just(patient1)

        val response = client.get()
            .uri("/v1/patients")
            .accept(MediaType.APPLICATION_JSON)
            .exchange() //invoking the end point
            .expectStatus().is2xxSuccessful
            .returnResult<Any>()
            .responseBody

        response.blockFirst() shouldBe expectedResult

        verify(exactly = 1) {
            patientService.findAll()

        }
    }
    @Test
    fun Test2(){
        val patient1 = Patient(
            patientId = "1",
            patientFirstName = "abc",
            patientLastname = "xyz",
            userName = "dk",
            mobileNumber = "12345",
            email = "abc",
            gender = "m",
            dob = "26121996",
            password = "abx1",
            address = "xzy"
        )
        val expectedResult = mapOf(
            "patientId" to "1",
            "patientFirstName" to "abc",
            "patientLastName" to "xyz",
            "userName" to "dk",
            "mobileNumber" to "12345",
            "email" to "abc",
            "gender" to "m",
            "dob" to "26121996",
            "password" to "abx1",
            "address" to "xzy"
        )

        every {
            patientService.findAll()
        } returns Flux.just(patient1)

        val response = client.get()
            .uri("/v1/1")
            .accept(MediaType.APPLICATION_JSON)
            .exchange() //invoking the end point
            .expectStatus().is2xxSuccessful
            .returnResult<Any>()
            .responseBody

        response.blockFirst() shouldBe expectedResult

        verify(exactly = 1) {
            patientService.findOnePatient("1")
        }
    }
    @Test
    fun Test3(){
        val patient1 = Patient(
            patientId = "1",
            patientFirstName = "abc",
            patientLastname = "xyz",
            userName = "dk",
            mobileNumber = "12345",
            email = "abc",
            gender = "m",
            dob = "26121996",
            password = "abx1",
            address = "xzy"
        )
        val expectedResult = mapOf(
            "patientId" to "1",
            "patientFirstName" to "abc",
            "patientLastName" to "xyz",
            "userName" to "dk",
            "mobileNumber" to "12345",
            "email" to "abc",
            "gender" to "m",
            "dob" to "26121996",
            "password" to "abx1",
            "address" to "xzy"
        )
every {
    patientService.createPatient(patient1)
}  returns Mono.just(patient1)
        val response = client.post()
            .uri("/v1/patients")
            .bodyValue(patient1)
            .exchange()
            .expectStatus().is2xxSuccessful
            .returnResult<Any>().responseBody

        response.blockFirst() shouldBe expectedResult

        verify(exactly = 1) {
            patientService.createPatient(patient1)
        }
    }
    @Test
    fun Test4(){
        val expectedResult = listOf( mapOf(
            "patientId" to "1",
            "patientFirstName" to "abc",
            "patientLastName" to "xyz",
            "userName" to "dk",
            "mobileNumber" to "12345",
            "email" to "abc",
            "gender" to "m",
            "dob" to "26121996",
            "password" to "abx1",
            "address" to "xzy"
        ))
        val patient1 = Patient(
            patientId = "1",
            patientFirstName = "abc",
            patientLastname = "xyz",
            userName = "dk",
            mobileNumber = "12345",
            email = "abc",
            gender = "m",
            dob = "26121996",
            password = "abx1",
            address = "xzy"
        )
        every {
            patientService.updatePatient(patient1)
        } returns Mono.just(patient1)
        val response = client.put()
            .uri("/v1/update/1")
            .bodyValue(patient1)
            .exchange()
            .expectStatus().is2xxSuccessful

        verify(exactly = 1) {
            patientService.updatePatient(patient1)
        }
    }

}

