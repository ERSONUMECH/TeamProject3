package com.medical.backend.model

import org.springframework.data.annotation.Id

data class HospitalList(

    @Id
    var hospitalId: String?,
    var hospitalName: String?,
    var hospitalPhNum: String?,
    var hospitalAddress :String?,
) {
    infix fun shouldBe(hospitalList: BookAppointment) {


    }
}