package com.urlchecker

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class UrlCheckerApplication

fun main(args: Array<String>) {
	runApplication<UrlCheckerApplication>(*args)
}
