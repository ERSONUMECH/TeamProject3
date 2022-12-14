package com.medical.backend.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler


@ControllerAdvice
class ControllerAdvice {
    @ExceptionHandler(NullPointerException::class)
    fun nullPointerException() : ResponseEntity<String> {
        return ResponseEntity("Null Pointer Exception Occured", HttpStatus.INTERNAL_SERVER_ERROR)
    }

    @ExceptionHandler(IndexOutOfBoundsException::class)
    fun indexOutOfBoundException() : ResponseEntity<String> {
        return ResponseEntity("Index out Of bound Exception Occured", HttpStatus.INTERNAL_SERVER_ERROR)
    }

    @ExceptionHandler(Exception::class)
    fun genericExceptionHandler(): ResponseEntity<String> {
        return ResponseEntity("Random Exception Occured", HttpStatus.INTERNAL_SERVER_ERROR)
    }

    @ExceptionHandler(InvalidInputException::class)
    fun invalidInputExceptionHandler(exception: InvalidInputException) : ResponseEntity<String> {
        return ResponseEntity("${exception.message}", HttpStatus.INTERNAL_SERVER_ERROR)
    }
    @ExceptionHandler
    fun handleArticleNotFoundException(ex: ArticleNotFoundException): ResponseEntity<ExceptionControlAdvice.ErrorMessageModel> {
        val errorMessage = ExceptionControlAdvice.ErrorMessageModel(
            HttpStatus.NOT_FOUND.value(),
            ex.message
        )
        return ResponseEntity(errorMessage, HttpStatus.NOT_FOUND)
    }

    class ArticleNotFoundException(message: String) : RuntimeException(message)
    {

    }
}