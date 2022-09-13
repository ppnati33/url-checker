package com.urlchecker

import com.ninjasquad.springmockk.MockkBean
import com.urlchecker.service.UrlCheckJobRescheduleHandler
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.TestPropertySource

@SpringBootTest(classes = [UrlCheckerApplication::class], webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ActiveProfiles("test")
@TestPropertySource(
    properties = [
        "spring.autoconfigure.exclude=" +
            "org.springframework.boot.autoconfigure.quartz.QuartzAutoConfiguration"
    ]
)
abstract class AbstractIntegrationTest {

    @MockkBean
    private lateinit var urlCheckJobRescheduleHandler: UrlCheckJobRescheduleHandler
}