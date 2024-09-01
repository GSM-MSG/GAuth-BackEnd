package com.msg.gauth

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching
import java.util.*

@SpringBootApplication
@ConfigurationPropertiesScan
@EnableCaching
class GauthBackendApplication

fun main(args: Array<String>) {
    TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"))
    runApplication<GauthBackendApplication>(*args)
}