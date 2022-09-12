package com.urlchecker.service

import com.urlchecker.exception.EntityNotFoundException
import com.urlchecker.model.CheckResult
import com.urlchecker.repository.CheckResultRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service

@Service
class CheckResultStorageService(
    private val checkResultRepository: CheckResultRepository
) {

    fun findAll(page: Int, size: Int): Page<CheckResult> {
        val pageResult = checkResultRepository.findAll(PageRequest.of(page, size))
        if (page >= pageResult.totalPages) {
            throw EntityNotFoundException("No results found for requested params: page=$page, size=$size")
        }
        return pageResult
    }

    fun saveAll(checkResults: List<CheckResult>) {
        checkResultRepository.saveAll(checkResults)
    }
}