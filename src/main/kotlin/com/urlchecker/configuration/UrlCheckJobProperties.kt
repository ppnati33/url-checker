package com.urlchecker.configuration

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "jobs.url-check")
class UrlCheckJobProperties {
    lateinit var jobTriggerName: String
    lateinit var jobName: String
    var repeatIntervalSec: Int = 0
}
