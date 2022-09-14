package com.medical.backend.repository


import com.medical.backend.model.HospitalList

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import org.springframework.dao.DuplicateKeyException

@DataMongoTest
class HospitalListRepositoryTest {
    @Autowired
    lateinit var hospitalListRepository: HospitalListRepository

    @BeforeEach
    fun tearDown() {
        hospitalListRepository.deleteAll().subscribe()
    }

    @Test
    fun `should find book for given id `() {

        val hospitalList = HospitalList(hospitalId = "1", hospitalName = "12345", hospitalPhNum = "321456", hospitalAddress = "abc")
        hospitalListRepository.save(hospitalList).block()

        val actualSaveApplication = hospitalListRepository.findById(
            "1"
        ).block()

//        if (actualSaveApplication != null) {
//            actualSaveApplication shouldBe hospitalList
//        }
    }

    @Test
    fun `should not store duplicate application with same id`() {
        val hospitalList1 = HospitalList(hospitalId = "1", hospitalName = "12345", hospitalPhNum = "321456", hospitalAddress = "abc")
        val hospitalList2 = HospitalList(hospitalId = "1", hospitalName = "1245", hospitalPhNum = "320456", hospitalAddress = "adbc")



        hospitalListRepository.insert(hospitalList1).block()

        assertThrows<DuplicateKeyException> { hospitalListRepository.insert(hospitalList2).block() }
    }
}