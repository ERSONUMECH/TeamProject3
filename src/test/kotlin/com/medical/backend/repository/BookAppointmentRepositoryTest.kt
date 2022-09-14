package com.medical.backend.repository

import com.medical.backend.model.BookAppointment
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import org.springframework.dao.DuplicateKeyException


@DataMongoTest


class BookAppointmentRepositoryTest {

    @Autowired
    lateinit var bookAppointmentRepository : BookAppointmentRepository

    @BeforeEach
    fun tearDown() {
        bookAppointmentRepository.deleteAll().subscribe()
    }

    @Test
    fun Test1() {

        val bookAppointment = BookAppointment("1","abc" , "xyz" , "pune","def", mobileNumber = "123",dateofAppointment = "26122021", reason = "fever")

        bookAppointmentRepository.save(bookAppointment).block()

        val actualSaveApplication = bookAppointmentRepository.findById(
            "1"
        ).block()

        if (actualSaveApplication != null) {
            actualSaveApplication shouldBe bookAppointment
        }
    }

    @Test
    fun Test2() {

        val bookAppointment1 = BookAppointment(patientId = "1", patientName = "abc", doctorName = "xyz", address = "cde", email= "abcd", mobileNumber = "12345", dateofAppointment = "23122021", reason = "fever")
        val bookAppointment2 = BookAppointment("1","def" ,"abcd1" , "fed" , "xyz", mobileNumber = "54321", dateofAppointment = "26122021", reason = "malaria")

        bookAppointmentRepository.insert(bookAppointment1).block()

        assertThrows<DuplicateKeyException> { bookAppointmentRepository.insert(bookAppointment2).block() }

    }

}