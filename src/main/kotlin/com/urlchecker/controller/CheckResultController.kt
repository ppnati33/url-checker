package com.urlchecker.controller

import com.urlchecker.controller.dto.*
import com.urlchecker.util.successfulResponse
import com.urlchecker.service.CheckResultStorageService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springdoc.core.converters.models.Pageable
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/checkResults", produces = [APPLICATION_JSON_VALUE])
class CheckResultController(
    private val checkResultStorageService: CheckResultStorageService
) {

    @GetMapping
    @ApiResponses(
        ApiResponse(
            description = "Successfully get all check results with pagination",
            responseCode = "200",
            content = [Content(schema = Schema(implementation = SuccessfulResponse::class))]
        ),
        ApiResponse(
            description = "Internal server error",
            responseCode = "500",
            content = [Content(schema = Schema(implementation = ErrorResponse::class))]
        )
    )
    @Operation(summary = "Get all check results")
    fun getAll(pageable: Pageable): SuccessfulResponse<PageableResponse<CheckResultDto>> = successfulResponse(
        checkResultStorageService.findAll(pageable.page, pageable.size).let { page ->
            PageableResponse(
                page = Pageable(page.number, page.size, listOf()),
                totalPages = page.totalPages,
                results = page.content.map { it.toDto() }
            )
        }
    )
}