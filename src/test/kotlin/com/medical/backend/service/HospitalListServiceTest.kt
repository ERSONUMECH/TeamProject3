package com.medical.backend.service

import com.medical.backend.model.HospitalList
import com.medical.backend.repository.HospitalListRepository
import io.kotlintest.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.test.StepVerifier

class HospitalListServiceTest {

    // mocking the repository layer response

    val hospitalList1 = HospitalList(hospitalId = "1", hospitalName = "medicare", hospitalPhNum = "12345", hospitalAddress = "xyz")
    val hospitalList2 = HospitalList(hospitalId = "1", hospitalName = "healthcare", hospitalPhNum = "12345", hospitalAddress = "xyz")

    private val hospitalListRepository = mockk<HospitalListRepository>(){

        every {
            findAll()
        } returns Flux.just(hospitalList1,hospitalList2)

        every {
            findById("1")
        } returns Mono.just(hospitalList1)
    }

    private val hospitalListService = HospitalListService(hospitalListRepository)


    @Test
    fun `should return all hospital lists when find all method is called`() {

        val firstHospitalList =  hospitalListService.findAll().blockFirst()
        val secondHospitalList = hospitalListService.findAll().blockLast()

        firstHospitalList shouldBe  hospitalList1
        secondHospitalList shouldBe hospitalList2
    }

    @Test
    fun `should expect on complete call post all the hospital list are retrieved`() {

        //StepVerifier takes care of subscribing

        StepVerifier.create( hospitalListService.findAll()).expectSubscription().expectNext(hospitalList1).expectNext(hospitalList2).verifyComplete()
        StepVerifier.create( hospitalListService.findAll()).expectNextCount(2).verifyComplete()
    }

//    @Test
//    fun `should find the list of books on the basis of the id`() {
//
//        val result = hospitalListService.findOneHospitalList("1")
//
//        result shouldBe hospitalList1
}
//}

