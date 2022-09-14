package com.medical.backend.service

import com.medical.backend.exception.ControllerAdvice
import com.medical.backend.model.BookAppointment
import com.medical.backend.repository.BookAppointmentRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.annotation.Id
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import javax.annotation.PostConstruct


@Service
class BookAppointmentService(
    @Autowired
    val bookAppointmentRepository: BookAppointmentRepository
    )
{

    fun addAppointment(bookAppointment: BookAppointment):Mono<BookAppointment>{
        return bookAppointmentRepository.save(bookAppointment)
    }


    fun updateAppointment(bookAppointment: BookAppointment):Mono<BookAppointment>{
        return bookAppointmentRepository.save(bookAppointment)
    }

    fun deleteById(id: String): Mono<Void> {
        return bookAppointmentRepository.deleteById(id)
    }
    fun findAll(): Flux<BookAppointment> = bookAppointmentRepository.findAll()
    fun `create-bookAppointment`(bookAppointment: BookAppointment):Mono<BookAppointment> {
        return bookAppointmentRepository.save(bookAppointment)
    }
    lateinit var bookAppointments: List<BookAppointment>

    @PostConstruct
    fun bookappointment() {
        bookAppointments = listOf(
            BookAppointment("1", "Piyush Kumar", "A.K.Jha", "Patna","abc@xyz","123456","26121996","fever"),
            BookAppointment("2", "ABC","VKSinha","Patna","cdc@abc","54321","23122021","Dengue"),
            BookAppointment("3", "SKJha","Rahul","Pune","cbc@bca","987654","21092021","Malaria")
        )
    }
    fun createAppointment(patientId: String): BookAppointment {
        val appointment = (bookAppointments.find { bookappointment -> bookappointment.patientName == patientId })
        if (appointment != null) {
            throw IllegalStateException("Patients with the same title already exists")
        }
        return BookAppointment("4","","","","","","","")
    }
    fun getAppointment(patientId: String): BookAppointment {
        return bookAppointments.find { bookappointment -> bookappointment.patientId == patientId }
            ?: throw ControllerAdvice.ArticleNotFoundException("Patient not found")
    }
    fun updateArticle(patientId: String, patientName:String): BookAppointment {
        val appointment = (bookAppointments.find { bookappointment -> bookappointment.patientName == patientName }
            ?: throw ControllerAdvice.ArticleNotFoundException("Article not found"))
        if (patientName.length > 50) {
            throw IllegalArgumentException("Patient's Name too long")
        }
        appointment.patientName = patientName
        return appointment
    }
}