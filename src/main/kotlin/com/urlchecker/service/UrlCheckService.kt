package com.urlchecker.service

import com.urlchecker.model.CheckResult
import com.urlchecker.model.Url
import kotlinx.coroutines.ExecutorCoroutineDispatcher
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service

import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice

@Service
class UrlCheckService(
    private val urlStorageService: UrlStorageService,
    private val checkResultStorageService: CheckResultStorageService,
    private val webClientFacade: WebClientFacade,
    private val asyncDispatcher: ExecutorCoroutineDispatcher
) {

    suspend fun processAllUrls(): Unit = coroutineScope {
        var pageable: Pageable = PageRequest.of(DEFAULT_START_PAGE, DEFAULT_PAGE_SIZE)
        while (true) {
            val slice: Slice<Url> = urlStorageService.findAll(pageable)

            val checkResults: List<CheckResult> = slice.content
                .map {
                    async(asyncDispatcher) {
                        webClientFacade.checkAccess(it.url)
                    }
                }
                .awaitAll()

            checkResultStorageService.saveAll(checkResults)

            if (!slice.hasNext()) {
                return@coroutineScope
            }

            pageable = slice.nextPageable()
        }
    }

    companion object {
        const val DEFAULT_START_PAGE = 0
        const val DEFAULT_PAGE_SIZE = 100
    }
}