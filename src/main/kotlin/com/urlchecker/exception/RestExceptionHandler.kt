package com.urlchecker.exception

import com.urlchecker.controller.dto.ErrorResponse
import com.urlchecker.util.errorResponse
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class RestExceptionHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleEntityNotFoundException(exception: EntityNotFoundException): ErrorResponse = handleException(exception)

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun handleGeneralException(exception: Exception): ErrorResponse = handleException(exception)

    private fun handleException(exception: Exception): ErrorResponse {
        logger.error(exception.message)
        return errorResponse(exception.message ?: "UnknownError")
    }

    companion object {
        private val logger = LoggerFactory.getLogger(RestExceptionHandler::class.java)
    }
}