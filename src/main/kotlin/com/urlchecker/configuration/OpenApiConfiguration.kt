package com.urlchecker.configuration

import com.urlchecker.UrlCheckerApplication
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springframework.boot.info.GitProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OpenApiConfiguration(
    private val gitProperties: GitProperties
) {

    @Bean
    fun openApi(): OpenAPI = with(gitProperties) {
        OpenAPI().info(
            Info()
                .title(UrlCheckerApplication::class.java.simpleName)
                .description("\"${get("commit.id.abbrev")} | ${get("commit.message.full")}\"")
                .version(get("closest.tag.name"))
        )
    }
}