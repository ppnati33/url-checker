package com.urlchecker.service

import com.urlchecker.configuration.UrlCheckJobProperties
import org.quartz.SimpleScheduleBuilder
import org.quartz.TriggerBuilder
import org.quartz.TriggerKey
import org.springframework.scheduling.quartz.SchedulerFactoryBean
import org.springframework.stereotype.Component

@Component
class UrlCheckJobRescheduleHandler(
    private val schedulerFactoryBean: SchedulerFactoryBean,
    private val properties: UrlCheckJobProperties
) {

    fun reschedule(repeatIntervalSec: Int) {
        val newScheduleBuilder = SimpleScheduleBuilder
            .simpleSchedule()
            .withIntervalInSeconds(repeatIntervalSec)
            .repeatForever()

        val newTrigger = TriggerBuilder.newTrigger()
            .forJob(properties.jobName)
            .withIdentity(properties.jobTriggerName)
            .withSchedule(newScheduleBuilder)
            .build()

        schedulerFactoryBean.scheduler.rescheduleJob(
            TriggerKey.triggerKey(properties.jobTriggerName),
            newTrigger
        )
    }
}