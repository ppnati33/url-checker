package com.urlchecker.controller

import com.urlchecker.controller.dto.PageDto
import com.urlchecker.controller.dto.PageResponse
import com.urlchecker.service.UrlStorageService
import io.swagger.v3.oas.annotations.Operation
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
    @Operation(summary = "Get all urls from check list")
    fun findAll(pageDto: PageDto): PageResponse<String> =
        urlStorageService.findAllUrls(pageDto.page, pageDto.size).let { page ->
            PageResponse(
                page = PageDto(
                    page = page.number,
                    size = page.size
                ),
                totalPages = page.totalPages,
                results = page.content.map { it.url }
            )
        }

    @DeleteMapping("/{url}")
    @Operation(summary = "Delete url from check list")
    fun delete(@PathVariable url: String): Unit = urlStorageService.delete(url)
}

