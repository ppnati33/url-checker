package com.urlchecker.controller.dto

import com.fasterxml.jackson.annotation.JsonPropertyOrder
import org.springdoc.core.converters.models.Pageable

@JsonPropertyOrder("page", "totalPages", "results")
data class PageableResponse<T>(
    val page: Pageable,
    val totalPages: Int,
    val results: List<T>
)
