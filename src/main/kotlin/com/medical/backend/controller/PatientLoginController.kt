package com.medical.backend.controller
import com.medical.backend.model.Patient
import com.medical.backend.repository.PatientRepository
import com.medical.backend.service.PatientLoginService
import com.medical.backend.service.PatientService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono


@RestController

class PatientLoginController(

    @Autowired
    val patientRepository: PatientRepository,
    val patientService: PatientService,
    val patientLoginService: PatientLoginService
) {

    @PostMapping("/signin")
    fun authenticateUser(@RequestBody patient: Patient) : Mono<Patient>
    {
        return patientLoginService.patientLogin(patient)
  }
}