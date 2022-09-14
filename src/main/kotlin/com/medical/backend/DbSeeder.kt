//package com.medical.backend
//
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.boot.CommandLineRunner
//import org.springframework.data.mongodb.core.ReactiveMongoOperations
//import reactor.core.publisher.Flux
//import reactor.core.publisher.Mono
//
//class DbSeeder  (
//    @Autowired
//    val adminRepository: BookAppointmentRepository,
//    @Autowired
//    val reactiveMongoOperations: ReactiveMongoOperations,)
//    : CommandLineRunner {
//    val adminList = Flux.just(
//        BookAppointment(id = null,name = "Piyush",position = "Manager",email = "abc@xyz.com",password = "0P@.in1p",number = 1234567809),
//        BookAppointment(id = null,name = "Chaitali",position = "Manager",email = "abc@xyz.com",password = "0P@.in1p",number = 1234567809),
//        BookAppointment(id = null,name = "Hemant",position = "Manager",email = "abc@xyz.com",password = "0P@.in1p",number = 1234567809),
//
//        )
//
//    override fun run(vararg args: String?) {
//        dbSetup()
//    }
//
//    private fun dbSetup() {
//        val admins = adminList.flatMap {
//            adminRepository.save(it)
//        }
//        reactiveMongoOperations.collectionExists(BookAppointment::class.java)
//            .flatMap {
//                when(it){
//                    true -> reactiveMongoOperations.dropCollection(BookAppointment::class.java)
//                    false -> Mono.empty()
//                }
//            }
//            .thenMany(reactiveMongoOperations.createCollection(BookAppointment::class.java))
//            .thenMany(admins)
//            .thenMany(adminRepository.findAll())
//            .subscribe({ println(it)}, {error -> println( error)}, { println(" -- Database has been initialized")
//            })
//    }
//} {
//}