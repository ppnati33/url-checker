package com.urlchecker.controller.dto

import com.urlchecker.model.enums.CheckResultType
import java.time.Instant

data class CheckResultDto(
    val url: String,
    val checkResult: CheckResultType,
    val checkTime: Instant
)
