package com.urlchecker.configuration

import com.urlchecker.service.UrlCheckService
import kotlinx.coroutines.runBlocking
import org.quartz.*
import org.slf4j.LoggerFactory
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.quartz.QuartzJobBean

@Configuration
@EnableConfigurationProperties(value = [UrlCheckJobProperties::class])
class UrlCheckJobConfiguration(private val properties: UrlCheckJobProperties) {

    @Bean(name = ["UrlCheckJob"])
    fun job(): JobDetail = JobBuilder
        .newJob(UrlCheckJob::class.java)
        .withIdentity(properties.jobName)
        .requestRecovery()
        .storeDurably()
        .build()

    @Bean(name = ["UrlCheckJobTrigger"])
    fun trigger(): Trigger {
        val scheduleBuilder = SimpleScheduleBuilder
            .simpleSchedule()
            .withIntervalInSeconds(properties.repeatIntervalSec)
            .repeatForever()

        return TriggerBuilder.newTrigger()
            .forJob(properties.jobName)
            .withIdentity(properties.jobTriggerName)
            .withSchedule(scheduleBuilder)
            .build()
    }

    @DisallowConcurrentExecution
    class UrlCheckJob(private val urlCheckService: UrlCheckService) : QuartzJobBean() {

        override fun executeInternal(context: JobExecutionContext) {
            val jobName = context.jobDetail.key.name
            logger.info("$jobName started")
            runBlocking { urlCheckService.processAllUrls() }
            logger.info("$jobName finished")
        }

        companion object {
            private val logger = LoggerFactory.getLogger(UrlCheckJob::class.java)
        }

    }
}