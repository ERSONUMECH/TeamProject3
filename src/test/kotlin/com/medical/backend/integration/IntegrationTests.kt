package com.medical.backend.integration

import com.medical.backend.model.BookAppointment
import com.medical.backend.repository.BookAppointmentRepository
import com.medical.backend.repository.HospitalListRepository
import com.medical.backend.repository.PatientRepository
import io.kotlintest.shouldBe
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.returnResult

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension::class)
class IntegrationTests {
    @Autowired
    lateinit var client : WebTestClient

    @Autowired
    lateinit var bookAppointmentRepository: BookAppointmentRepository

    @Autowired
    lateinit var hospitalListRepository: HospitalListRepository

    @Autowired
    lateinit var patientRepository: PatientRepository

    @BeforeEach
    fun setUp1() {
        bookAppointmentRepository.deleteAll().subscribe()
    }

    @BeforeEach
    fun setUp2() {
        hospitalListRepository.deleteAll().subscribe()
    }

    @BeforeEach
    fun setUp3() {
        patientRepository.deleteAll().subscribe()
    }
@Test
fun Test1(){
    val expectedResult = mapOf("patientid" to "1",
        "patientName" to "abc",
        "doctorName" to "abcd",
        "address" to "xyz",
        "email" to "adx",
        "mobileNumber" to "123",
        "dateofAppointment" to "23122021",
        "reason" to "fever")
    val bookAppointment1 = BookAppointment("1","abc" ,"abcd" , "xyz","adx", mobileNumber = "123", dateofAppointment = "23122021", reason = "fever")
    val response = client.post()
        .uri("//bookAppointment")
        .bodyValue(bookAppointment1)
        .exchange()
        .expectStatus().is2xxSuccessful
        .returnResult<Any>()
        .responseBody
        .blockFirst()

    response shouldBe expectedResult
}
    @Test
    fun Test2(){
        val expectedResult = mapOf("patientid" to "1",
            "patientName" to "abc",
            "doctorName" to "abcd",
            "address" to "xyz",
            "email" to "adx",
            "mobileNumber" to "123",
            "dateofAppointment" to "23122021",
            "reason" to "fever")
        val bookAppointment1 = BookAppointment("1","abc" ,"abcd" , "xyz","adx", mobileNumber = "123", dateofAppointment = "23122021", reason = "fever")
        val bookRequest = BookAppointment("1","abc" ,"abcd" , "xyz","adx", mobileNumber = "123", dateofAppointment = "23122021", reason = "fever")

   bookAppointmentRepository.save(bookAppointment1).block()
        val response = client.put()
            .uri("/updateAppointment/1")
            .bodyValue(bookRequest)
            .exchange()
            .expectStatus().is2xxSuccessful
            .returnResult<Any>()
            .responseBody
            .blockFirst()

        response shouldBe expectedResult
    }
}