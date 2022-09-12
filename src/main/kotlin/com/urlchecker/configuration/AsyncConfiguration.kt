package com.urlchecker.configuration

import kotlinx.coroutines.asCoroutineDispatcher
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.concurrent.CustomizableThreadFactory
import java.util.concurrent.LinkedBlockingDeque
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

@EnableConfigurationProperties(value = [ExecutorProperties::class])
@Configuration
class AsyncConfiguration(private val properties: ExecutorProperties) {

    @Bean
    fun asyncExecutor(): ThreadPoolExecutor =
        ThreadPoolExecutor(
            properties.corePoolSize,
            properties.maxPoolSize,
            properties.keepAliveSeconds,
            TimeUnit.SECONDS,
            LinkedBlockingDeque(properties.queueCapacity),
            CustomizableThreadFactory("async-thread-")
        )

    @Bean
    fun asyncDispatcher(asyncExecutor: ThreadPoolExecutor) = asyncExecutor.asCoroutineDispatcher()
}

@ConfigurationProperties(prefix = "async-executor")
class ExecutorProperties {
    var corePoolSize: Int = 0
    var maxPoolSize: Int = corePoolSize * 2
    var queueCapacity: Int = 0
    var keepAliveSeconds: Long = 0
}