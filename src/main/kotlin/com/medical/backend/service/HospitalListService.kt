package com.medical.backend.service

import com.medical.backend.exception.ControllerAdvice
import com.medical.backend.model.BookAppointment
import com.medical.backend.model.HospitalList
import com.medical.backend.repository.HospitalListRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import javax.annotation.PostConstruct


@Service
class HospitalListService(
    @Autowired
    val hospitalListRepository: HospitalListRepository

) {

    fun findAllHospitalLists() : Flux<HospitalList> {
        return hospitalListRepository.findAll()
    }

    fun addToHospitalList(HospitalList: HospitalList):Mono<HospitalList>{
        return hospitalListRepository.save(HospitalList)
    }


    fun updateHospitalList(HospitalList1: String, HospitalList: HospitalList):Mono<HospitalList>{
        return hospitalListRepository.save(HospitalList)
    }

    fun deleteByhospitalId(hospitalId: String): Mono<Void> {
        return hospitalListRepository.deleteById(hospitalId)
    }
    fun remove() : Mono<Void> {
        return hospitalListRepository.deleteAll()
    }

    fun findByhospitalId(hospitalId: String): Mono<HospitalList>{
        return hospitalListRepository.findById(hospitalId)
    }

    fun findAll() :Flux<HospitalList> = hospitalListRepository.findAll()
    fun findOneHospitalList(hospitalId: String): Mono<HospitalList> {
        return hospitalListRepository.findById(hospitalId)
    }

    fun createHospitalList(hospitalList: HospitalList): Mono<HospitalList> {
        return hospitalListRepository.save(hospitalList)
    }

    fun updateBookById(hospitalId: String, hospitalList: HospitalList):Mono<HospitalList> {
return hospitalListRepository.findById(hospitalId)
    .flatMap {
        it.hospitalId = hospitalList.hospitalId
        it.hospitalAddress= hospitalList.hospitalAddress
        it.hospitalName = hospitalList.hospitalName
        it.hospitalPhNum = hospitalList.hospitalPhNum
        hospitalListRepository.save(it)
    }
    }
    lateinit var hospitalLists: List<HospitalList>

    @PostConstruct
    fun hospitalList() {
        hospitalLists = listOf(
            HospitalList("1", "Medicare", "12345","Patna"),
            HospitalList("2","Bone And Spine","12345","Bangalore"),
            HospitalList("3","Cancer Trauma","543210","Mumbai")
           )
    }
    fun createHospital(hospitalName: String): HospitalList {
        val hospitalList = (hospitalLists.find { hospitalList -> hospitalList.hospitalName == hospitalName })
        if (hospitalList != null) {
            throw IllegalStateException("Hospital with the same name already exists")
        }
        return HospitalList("4","","","")
    }
    fun getHospitalList(hospitalId: String): HospitalList {
        return hospitalLists.find { hospitalList -> hospitalList.hospitalId == hospitalId }
            ?: throw ControllerAdvice.ArticleNotFoundException("Patient not found")
    }
    fun updateArticle(hospitalId: String, hospitalName: String): HospitalList {
        val hospitalList = (hospitalLists.find { bookappointment -> bookappointment.hospitalName == hospitalName }
            ?: throw ControllerAdvice.ArticleNotFoundException("Hospital not found"))
        if (hospitalName.length > 50) {
            throw IllegalArgumentException("Hospital's Name too long")
        }
        hospitalList.hospitalName = hospitalName
        return hospitalList
    }
}