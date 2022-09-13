package com.urlchecker.controller

import com.urlchecker.controller.dto.PageableResponse
import com.urlchecker.controller.dto.SuccessfulResponse
import com.urlchecker.service.UrlStorageService
import com.urlchecker.util.emptyResponse
import com.urlchecker.util.successfulResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springdoc.core.converters.models.Pageable
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/urls", produces = [MediaType.APPLICATION_JSON_VALUE])
class UrlController(
    private val urlStorageService: UrlStorageService
) {

    @PostMapping
    @Operation(summary = "Add url to check list")
    fun add(@RequestBody url: String): String = urlStorageService.save(url)

    @GetMapping
    @ApiResponses(
        ApiResponse(
            description = "Successfully get all urls from check list with pagination",
            responseCode = "200",
            useReturnTypeSchema = true
        ),
        ApiResponse(
            description = "Internal server error",
            responseCode = "500",
            useReturnTypeSchema = true
        )
    )
    @Operation(summary = "Get all urls from check list")
    fun findAll(pageable: Pageable): SuccessfulResponse<PageableResponse<String>> = successfulResponse(
        urlStorageService.findAllUrls(pageable.page, pageable.size).let { page ->
            PageableResponse(
                page = Pageable(page.number, page.size, listOf()),
                totalPages = page.totalPages,
                results = page.content.map { it.url }
            )
        }
    )

    @DeleteMapping("/{url}")
    @Operation(summary = "Delete url from check list")
    fun delete(@PathVariable url: String): SuccessfulResponse<Unit> {
        urlStorageService.delete(url)
        return emptyResponse()
    }
}

