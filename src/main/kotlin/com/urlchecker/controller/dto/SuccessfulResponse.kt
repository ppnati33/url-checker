package com.urlchecker.controller.dto

import com.urlchecker.controller.dto.ResponseType.SUCCESS

data class SuccessfulResponse<T>(
    val result: ResponseType = SUCCESS,
    val payload: T? = null
)