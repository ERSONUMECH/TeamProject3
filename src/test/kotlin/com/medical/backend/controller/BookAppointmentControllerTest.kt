package com.medical.backend.controller

import com.medical.backend.model.BookAppointment
import com.medical.backend.service.BookAppointmentService
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
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.returnResult
import reactor.core.publisher.Mono


@WebFluxTest(BookAppointmentController::class)
@AutoConfigureWebTestClient

class BookAppointmentControllerTest {
    //need to mock the service layer responses
    @Autowired
    lateinit var client : WebTestClient

    @Autowired
    lateinit var bookAppointmentService: BookAppointmentService

    @TestConfiguration
    class ControllerTestConfig {
        @Bean
        fun bookAppointmentService() = mockk<BookAppointmentService>()
    }

    @Test
    fun Test1() {

        val bookAppointment1 = BookAppointment("1","abc" ,"abcd" , "xyz","adx", "12345","23122021","fever")

         val expectedResult = mapOf("patientid" to "1",
            "patientName" to "abc",
            "doctorName" to "abcd",
            "address" to "xyz",
            "email" to "adx",
            "mobileNumber" to "123",
            "dateofAppointment" to "23122021",
             "reason" to "fever")

       every {
           bookAppointmentService.`create-bookAppointment`(bookAppointment1)
       } returns Mono.just(bookAppointment1)

        val response = client.post()
            .uri("v1/bookAppointment")
            .bodyValue(bookAppointment1)
            .exchange() //invoking the end point
            .expectStatus().is2xxSuccessful
            .returnResult<Any>()
            .responseBody

        response.blockFirst() shouldBe  expectedResult

        verify(exactly = 1){
            bookAppointmentService.addAppointment(bookAppointment1)
        }
    }

    @Test
    fun Test2() {

        val expectedResult = listOf(
            mapOf("patientid" to "1",
            "patientName" to "abc",
            "doctorName" to "abcd",
            "address" to "xyz",
            "email" to "adx",
            "mobileNumber" to "123",
            "dateofAppointment" to "23122021",
            "reason" to "fever")
        )
        val bookAppointment1 = BookAppointment("1","abc" ,"abcd" , "xyz","adx", mobileNumber = "123", dateofAppointment = "23122021", reason = "fever")

        every {
            bookAppointmentService.updateAppointment(bookAppointment1)
        } returns Mono.just(bookAppointment1)

        val response = client.put()
            .uri("v1/updateAppointment/1")
            .bodyValue(bookAppointment1)
            .exchange() //invoking the end point
            .expectStatus().is2xxSuccessful


        verify(exactly = 1) {
            bookAppointmentService.updateAppointment(bookAppointment1)
        }
    }


}


