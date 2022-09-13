package com.urlchecker.controller

import com.urlchecker.controller.dto.SuccessfulResponse
import com.urlchecker.util.emptyResponse
import com.urlchecker.service.UrlCheckJobRescheduleHandler
import io.swagger.v3.oas.annotations.Operation
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/configurations")
class ConfigurationController(
    private val urlCheckJobRescheduleHandler: UrlCheckJobRescheduleHandler
) {

    @PostMapping("/repeatInterval/{repeatIntervalSec}")
    @Operation(summary = "Update url check repeat interval in seconds")
    fun changeCheckRepeatInterval(@PathVariable repeatIntervalSec: Int): SuccessfulResponse<Unit> {
        urlCheckJobRescheduleHandler.reschedule(repeatIntervalSec)
        return emptyResponse()
    }

}