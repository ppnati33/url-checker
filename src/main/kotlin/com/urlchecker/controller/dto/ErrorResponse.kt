package com.urlchecker.controller.dto

import com.urlchecker.controller.dto.ResponseType.ERROR

data class ErrorResponse(
    val result: ResponseType = ERROR,
    val errorMessage: String
)