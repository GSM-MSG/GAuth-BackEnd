package com.msg.gauth

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication
@ConfigurationPropertiesScan
class GauthBackendApplication

fun main(args: Array<String>) {
    runApplication<GauthBackendApplication>(*args)
}
