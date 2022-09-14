//package com.medical.backend.service
//
//import com.medical.backend.model.BookAppointment
//import com.medical.backend.repository.BookAppointmentRepository
//import io.mockk.every
//import io.mockk.mockk
//import org.junit.jupiter.api.Test
//import reactor.core.publisher.Flux
//import reactor.core.publisher.Mono
//import reactor.test.StepVerifier
//
//class BookAppointmentServiceTest {
//// mocking the repository layer response
//
//    val bookAppointment1 = BookAppointment(patientId = "1", patientName = "abc", doctorName = "xyz", address = "cde", email= "abcd", mobileNumber = "12345", dateofAppointment = "23122021", reason = "fever")
//    val bookAppointment2 = BookAppointment("1","def" ,"abcd1" , "fed" , "xyz", mobileNumber = "54321", dateofAppointment = "26122021", reason = "malaria")
//
//
//    private val bookAppointmentRepository:BookAppointmentRepository = mockk(BookAppointmentRepository){
//
//        every {
//            findAll()
//        } returns Flux.just(bookAppointment1,bookAppointment2)
//
//        every {
//            findById("1")
//        } returns Mono.just(bookAppointment1)
//    }
//
//    private val bookAppointmentService = BookAppointmentService(bookAppointmentRepository)
//
//
//    @Test
//    fun Test1(){
//
//        val firstBookAppointment =  bookAppointmentService.findAll().blockFirst()
//        val secondBookAppointment = bookAppointmentService.findAll().blockLast()
//
//        if (firstBookAppointment != null) {
//            firstBookAppointment shouldBe bookAppointment1
//        }
//        if (secondBookAppointment != null) {
//            secondBookAppointment shouldBe bookAppointment2
//        }
//    }
//
//    @Test
//    fun Test2() {
//
//        //StepVerifier takes care of subscribing
//
//        StepVerifier.create( bookAppointmentService.findAll()).expectSubscription().expectNext(bookAppointment1).expectNext(bookAppointment2).verifyComplete()
//        StepVerifier.create( bookAppointmentService.findAll()).expectNextCount(2).verifyComplete()
//    }
//
////    @Test
////    fun Test3() {
////
////        val result = bookAppointmentService.findOneBookAppointment("1").block()
////
////        if (result != null) {
////            result shouldBe bookAppointment1
////        }
////    }
//
//}