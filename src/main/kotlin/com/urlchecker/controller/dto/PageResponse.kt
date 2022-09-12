package com.urlchecker.controller.dto

import com.fasterxml.jackson.annotation.JsonPropertyOrder

@JsonPropertyOrder("page", "totalPages", "results")
data class PageResponse<T>(
    val page: PageDto,
    val totalPages: Int,
    val results: List<T>
)
