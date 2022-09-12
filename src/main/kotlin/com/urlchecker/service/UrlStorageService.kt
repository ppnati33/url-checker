package com.urlchecker.service

import com.urlchecker.exception.EntityNotFoundException
import com.urlchecker.model.Url
import com.urlchecker.repository.UrlRepository
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.stereotype.Service
import java.time.Instant.now

@Service
class UrlStorageService(
    private val urlRepository: UrlRepository
) {

    fun delete(url: String): Unit = try {
        urlRepository.deleteById(url)
    } catch (e: EmptyResultDataAccessException) {
        throw EntityNotFoundException("No results found for requested url=$url")
    }

    fun save(url: String): String = urlRepository.save(Url(url, now())).url

    fun findAll(pageable: Pageable): Slice<Url> = urlRepository.findAll(pageable)

    fun findAllUrls(page: Int, size: Int): Page<Url> {
        val pageResult = urlRepository.findAll(PageRequest.of(page, size))
        if (page >= pageResult.totalPages) {
            throw EntityNotFoundException("No results found for requested params: page=$page, size=$size")
        }
        return pageResult
    }
}