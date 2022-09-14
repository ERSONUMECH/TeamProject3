package com.medical.backend.controller

import com.medical.backend.model.HospitalList
import com.medical.backend.service.HospitalListService
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

@WebFluxTest(HospitalListController::class)
@AutoConfigureWebTestClient

class HospitalListControllerTest {
    @Autowired
    lateinit var client : WebTestClient

    @Autowired
    lateinit var hospitalListService: HospitalListService

    @TestConfiguration
    class ControllerTestConfig {
        @Bean
        fun hospitalListService()= mockk<HospitalListService>()
    }

    @Test
    fun Test1() {

        val hospitalList1 = HospitalList(hospitalId = "1", hospitalName = "medicare", hospitalPhNum = "12345", hospitalAddress = "xyz")

        val expectedResult = mapOf("hospitalid" to "1",
            "hospitalName" to "abc",
            "hospitalPhNum" to "12345",
            "hospitalAddress" to "xyz")


        every{
            hospitalListService.findAll()
        }returns Flux.just(hospitalList1)

        val response = client.get()
            .uri("/hospital")
            .accept(MediaType.APPLICATION_JSON)
            .exchange() //invoking the end point
            .expectStatus().is2xxSuccessful
            .returnResult<Any>()
            .responseBody

        response.blockFirst() shouldBe expectedResult

        verify(exactly = 1) {
            hospitalListService.findAll()
        }
    }

    @Test
    fun Test2() {

        val hospitalList1 = HospitalList(hospitalId = "1", hospitalName = "medicare", hospitalPhNum = "12345", hospitalAddress = "xyz")

        val expectedResult = mapOf("hospitalid" to "1",
            "hospitalName" to "abc",
            "hospitalPhNum" to "12345",
            "hospitalAddress" to "xyz")

        every {
            hospitalListService.findOneHospitalList("1")
        } returns Mono.just(hospitalList1)

        val response = client.get()
            .uri("1")
            .accept(MediaType.APPLICATION_JSON)
            .exchange() //invoking the end point
            .expectStatus().is2xxSuccessful
            .returnResult<Any>()
            .responseBody

        response.blockFirst() shouldBe expectedResult

        verify(exactly = 1) {
            hospitalListService.findOneHospitalList("1")
        }
        @Test
        fun Test3(){
            val hospitalList1 = HospitalList(hospitalId = "1", hospitalName = "medicare", hospitalPhNum = "12345", hospitalAddress = "xyz")

            val expectedResult = mapOf("hospitalid" to "1",
                "hospitalName" to "abc",
                "hospitalPhNum" to "12345",
                "hospitalAddress" to "xyz")
            every {
                hospitalListService.findAll()
            } returns Flux.just(hospitalList1)


            val response = client.post()
                .uri("/hospitalLists")
                .bodyValue(hospitalList1)
                .exchange() //invoking the end point
                .expectStatus().is2xxSuccessful
                .returnResult<Any>()
                .responseBody
            response.blockFirst() shouldBe  expectedResult

            verify(exactly = 1){
                hospitalListService.createHospitalList(hospitalList1)
            }


        }
    }

    @Test
    fun Test3() {
        val expectedResult = mapOf("hospitalid" to "1",
            "hospitalName" to "abc",
            "hospitalPhNum" to "12345",
            "hospitalAddress" to "xyz")

        val hospitalList1 = HospitalList(hospitalId = "1", hospitalName = "medicare", hospitalPhNum = "12345", hospitalAddress = "xyz")


        every {
            hospitalListService.createHospitalList(hospitalList1)
        } returns Mono.just(hospitalList1)

        val response = client.post()
            .uri("/HospitalLists")
            .bodyValue(hospitalList1)
            .exchange()
            .expectStatus().is2xxSuccessful
            .returnResult<Any>().responseBody

        response.blockFirst() shouldBe expectedResult

        verify(exactly = 1) {
            hospitalListService.createHospitalList(hospitalList1)
        }
    }

    @Test
    fun Test4() {
        val expectedResult = mapOf("hospitalid" to "1",
            "hospitalName" to "abc",
            "hospitalPhNum" to "12345",
            "hospitalAddress" to "xyz")

        val hospitalList1 = HospitalList(hospitalId = "1", hospitalName = "medicare", hospitalPhNum = "12345", hospitalAddress = "xyz")
        every {
            hospitalListService.updateHospitalList("1",hospitalList1)
        } returns Mono.just(hospitalList1)




        val response = client.put()
            .uri("/updateHospitalList/1")
            .bodyValue(hospitalList1
            )
            .exchange()
            .expectStatus().is2xxSuccessful

        verify(exactly = 1) {
            hospitalListService.updateHospitalList("1",hospitalList1)
        }
        }
    }
