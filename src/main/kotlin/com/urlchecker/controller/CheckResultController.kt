package com.urlchecker.controller

import com.urlchecker.controller.dto.CheckResultDto
import com.urlchecker.controller.dto.PageDto
import com.urlchecker.controller.dto.PageResponse
import com.urlchecker.service.CheckResultStorageService
import io.swagger.v3.oas.annotations.Operation
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
    @Operation(summary = "Get all check results")
    fun getAll(pageDto: PageDto): PageResponse<CheckResultDto> =
        checkResultStorageService.findAll(pageDto.page, pageDto.size).let { page ->
            PageResponse(
                page = PageDto(
                    page = page.number,
                    size = page.size
                ),
                totalPages = page.totalPages,
                results = page.content.map { it.toDto() }
            )
        }
}