package com.urlchecker.service

import com.urlchecker.model.CheckResult
import com.urlchecker.model.enums.CheckResultType
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitExchange
import java.time.Instant

@Component
class WebClientFacade {

    suspend fun checkAccess(url: String): CheckResult {
        val webClient = WebClient.builder().baseUrl(url).build()
        val checkResult: CheckResultType = try {
            webClient
                .head()
                .awaitExchange { clientResponse ->
                    if (clientResponse.statusCode().isError) {
                        CheckResultType.NOT_OK
                    } else {
                        CheckResultType.OK
                    }
                }
        } catch (e: Exception) {

            logger.info("Error while trying to connect to $url")

            CheckResultType.NOT_OK
        }

        return CheckResult(
            checkResult = checkResult,
            checkTime = Instant.now(),
            url = url
        )
    }

    companion object {
        private val logger = LoggerFactory.getLogger(WebClientFacade::class.java)
    }
}