package com.urlchecker.controller

import com.urlchecker.controller.dto.CheckResultDto
import com.urlchecker.model.CheckResult

fun CheckResult.toDto() = CheckResultDto(
    url = url,
    checkResult = checkResult,
    checkTime = checkTime
)