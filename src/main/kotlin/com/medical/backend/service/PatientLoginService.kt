package com.medical.backend.service

import com.medical.backend.exception.ControllerAdvice
import com.medical.backend.model.HospitalList
import com.medical.backend.model.Patient
import com.medical.backend.repository.PatientRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.annotation.Id
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import javax.annotation.PostConstruct

@Service
class PatientLoginService(

    @Autowired
    val patientRepository: PatientRepository
) {
    fun patientLogin(patient :Patient ): Mono<Patient> {
        return patientRepository.save(patient)
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
        val patient = (patients.find { hospitalList -> hospitalList.patientFirstName == patientFirstName })
        if (patient != null) {
            throw IllegalStateException("Patient with the same name already exists")
        }
        return Patient("4","","","","","","","","","")
    }
    fun signInPatient(patientId:String): Patient {
        return patients.find { patient -> patient.patientId == patientId }
            ?: throw ControllerAdvice.ArticleNotFoundException("Patient not found")
    }

}