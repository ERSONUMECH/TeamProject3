package com.medical.backend.service

import com.medical.backend.exception.ControllerAdvice
import com.medical.backend.model.BookAppointment
import com.medical.backend.model.Patient
import com.medical.backend.repository.PatientRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import javax.annotation.PostConstruct


@Service
class PatientService(
    @Autowired
    val patientRepository: PatientRepository
    )

{
    fun findAllPatients() : Flux<Patient> {
        return patientRepository.findAll()
    }

    fun addPatient(patient: Patient): Mono<Patient> {
        return patientRepository.save(patient)

    }
    fun updatePatient( patient: Patient):Mono<Patient>{
        return patientRepository.save(patient)
    }

    fun deleteById(id: String): Mono<Void>{
        return patientRepository.deleteById(id)
    }


    fun findById(id: String): Mono<Patient>{
        return patientRepository.findById(id)
    }

    fun findAll() : Flux<Patient> {
        return patientRepository.findAll()
    }

    fun findOnePatient(patientId: String): Mono<Patient> {
return patientRepository.findById(patientId)
    }

    fun createPatient(patient1: Patient):Mono<Patient> {
     return patientRepository.save(patient1)
    }
    lateinit var patients: List<Patient>

    @PostConstruct
    fun patientList() {
        patients = listOf(
            Patient("1", "ABC", "XYZ","az","123","abc","male","26122020","abc123","Patna"),
            Patient("2","Piyush","Kumar","abcxyz","12345","cba","Male","25122020","123@abc","Bangalore"),
            Patient("3","Sonu","Kumar","abc123","54321","dcb","Male","25122021","123@cba","Mumbai")
        )
    }
    fun createPatient(patientFirstName:String): Patient {
        val patient = (patients.find { patient -> patient.patientFirstName == patientFirstName })
        if (patient != null) {
            throw IllegalStateException("Patient with the same name already exists")
        }
        return Patient("4","","","","","","","","","")
    }
    fun getPatient(patientId: String): Patient {
        return patients.find { patient -> patient.patientId == patientId }
            ?: throw ControllerAdvice.ArticleNotFoundException("Patient not found")
    }
    fun updateArticle(patientId: String, patientFirstName: String): BookAppointment {
        val patient = (patients.find { patient -> patient.patientFirstName == patientFirstName }
            ?: throw ControllerAdvice.ArticleNotFoundException("Patient not found"))
        if (patientFirstName.length > 50) {
            throw IllegalArgumentException("Patient's Name too long")
        }
        patient.patientFirstName = patientFirstName
        return patient
    }

}