package com.medical.backend.controller

import com.medical.backend.model.HospitalList
import com.medical.backend.repository.HospitalListRepository
import com.medical.backend.service.HospitalListService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono


@RestController
@RequestMapping("v1")
class HospitalListController (

    @Autowired
    val hospitalListRepository: HospitalListRepository,
    val hospitalListService: HospitalListService
){

    @GetMapping("/hospital")
    fun getAllHospitalLists(): Flux<HospitalList> {
        return hospitalListService.findAllHospitalLists()
    }

    @GetMapping("{hospitalId}")
    fun getHospitalListById(@PathVariable hospitalId: String): Mono<HospitalList> {
        return hospitalListService.findByhospitalId(hospitalId)
    }

    @PostMapping("/hospitalLists")
    fun HospitalListSave(@RequestBody HospitalList: HospitalList): Mono<HospitalList> {
        return hospitalListService.addToHospitalList(HospitalList)
    }

    @PutMapping("/updateHospitalList/{hospitalId}")
    fun updateByHospitalId( @RequestBody  hospitalList: HospitalList): Mono<HospitalList> {
        return hospitalListService.updateHospitalList("1", hospitalList)
    }


    @DeleteMapping("/HospitalLists/{hospitalId}")
    fun deleteHospitalLists(@PathVariable hospitalId: String): Mono<Void> {
        return hospitalListService.deleteByhospitalId(hospitalId)
    }
}


