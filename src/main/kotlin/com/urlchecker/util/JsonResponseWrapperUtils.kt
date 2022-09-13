package com.urlchecker.util

import com.urlchecker.controller.dto.ErrorResponse
import com.urlchecker.controller.dto.SuccessfulResponse

fun emptyResponse(): SuccessfulResponse<Unit> = SuccessfulResponse()

fun <T> successfulResponse(payload: T): SuccessfulResponse<T> = SuccessfulResponse(payload = payload)

fun errorResponse(message: String): ErrorResponse =
    ErrorResponse(errorMessage = message)