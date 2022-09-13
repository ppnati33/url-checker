package com.urlchecker.service

import com.urlchecker.model.CheckResult
import com.urlchecker.model.Url
import com.urlchecker.model.enums.CheckResultType
import io.mockk.*
import kotlinx.coroutines.ExecutorCoroutineDispatcher
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import java.time.Instant.now

class UrlCheckServiceTest {

    private val urlStorageService: UrlStorageService = mockk()
    private val checkResultStorageService: CheckResultStorageService = mockk()
    private val webClientFacade: WebClientFacade = mockk()
    private val asyncDispatcher: ExecutorCoroutineDispatcher =
        ThreadPoolTaskExecutor().apply {
            corePoolSize = 2
            initialize()
        }.asCoroutineDispatcher() as ExecutorCoroutineDispatcher

    private val urlCheckService: UrlCheckService =
        UrlCheckService(urlStorageService, checkResultStorageService, webClientFacade, asyncDispatcher)

    @Test
    fun `should successfully process url availability and save result`() {
        // ARRANGE
        val url = "https://url.com"
        val urls = listOf(Url(url, now()))
        coEvery { urlStorageService.findAll(any()) } returns PageImpl(urls)
        coEvery { webClientFacade.checkAccess(any()) } returns CheckResult(
            checkResult = CheckResultType.OK,
            checkTime = now(),
            url = url
        )
        coEvery { checkResultStorageService.saveAll(any()) } just runs

        val pageRequest = slot<Pageable>()
        val checkResults = slot<List<CheckResult>>()

        // ACT
        runBlocking { urlCheckService.processAllUrls() }

        // ASSERT
        coVerify(exactly = 1) {
            urlStorageService.findAll(capture(pageRequest))
            webClientFacade.checkAccess(url)
            checkResultStorageService.saveAll(capture(checkResults))
        }

        assertThat(pageRequest.captured.pageNumber).isEqualTo(0)
        assertThat(pageRequest.captured.pageSize).isEqualTo(100)
        assertThat(checkResults.captured.size).isEqualTo(1)
        assertThat(checkResults.captured.first().url).isEqualTo(url)
        assertThat(checkResults.captured.first().checkResult).isEqualTo(CheckResultType.OK)
    }

    @Test
    fun `should successfully process url availability hen no url found to process`() {
        // ARRANGE
        val urls: List<Url> = listOf()
        coEvery { urlStorageService.findAll(any()) } returns PageImpl(urls)
        coEvery { webClientFacade.checkAccess(any()) } answers {
            CheckResult(
                checkResult = CheckResultType.OK,
                checkTime = now(),
                url = firstArg()
            )
        }
        coEvery { checkResultStorageService.saveAll(any()) } just runs
        val checkResults = slot<List<CheckResult>>()

        // ACT
        runBlocking { urlCheckService.processAllUrls() }

        // ASSERT
        coVerify(exactly = 1) {
            urlStorageService.findAll(any())
            checkResultStorageService.saveAll(capture(checkResults))
        }

        coVerify(exactly = 0) {
            webClientFacade.checkAccess(any())
        }

        assertThat(checkResults.captured.size).isEqualTo(0)
    }

}