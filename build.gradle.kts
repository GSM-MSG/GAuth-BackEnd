import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version PluginVersion.SPRING_BOOT_VERSION
    id("io.spring.dependency-management") version PluginVersion.DEPENDENCY_MANAGER_VERSION
    id("com.ewerk.gradle.plugins.querydsl") version PluginVersion.QUERY_DSL_PLUGIN_VERSION
    id("org.jlleitschuh.gradle.ktlint") version "10.2.0"

    kotlin("jvm") version PluginVersion.JVM_VERSION
    kotlin("plugin.spring") version PluginVersion.SPRING_PLUGIN_VERSION
    kotlin("plugin.jpa") version PluginVersion.JPA_PLUGIN_VERSION
    kotlin("kapt") version PluginVersion.KAPT_VERSION
    idea
    application
}

group = "com.msg"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {

    // spring
    implementation(Dependencies.SPRING_JPA)
    implementation(Dependencies.SPRING_REDIS)
    implementation(Dependencies.SPRING_SECURITY)
    implementation(Dependencies.SPRING_WEB)
    implementation(Dependencies.SPRING_MAIL)
    implementation(Dependencies.SPRING_THYMELEAF)
    implementation(Dependencies.SPRING_VALIDATION)
    kapt(Dependencies.CONFIG_PROCESSOR)
    testImplementation(Dependencies.SPRING_STARTER_TEST)
    testImplementation(Dependencies.SPRING_SECURITY_TEST)
    implementation(Dependencies.SPRING_AOP)
    implementation(Dependencies.SPRING_ACTUATOR)

    // kotlin
    implementation(Dependencies.JACKSON_MODULE_KOTLIN)
    implementation(Dependencies.KOTLIN_REFLET)
    implementation(Dependencies.KOTLIN_JDK)

    // jackson
    implementation(Dependencies.KOTLIN_SERIALIZATION)

    // aws
    implementation(Dependencies.AWS_S3)

    // database
    runtimeOnly(Dependencies.H2_DATABASE)
    runtimeOnly(Dependencies.MYSQL_DATABASE)
    runtimeOnly(Dependencies.MARIADB_DATABASE)

    // jwt
    implementation(Dependencies.JWT_API)
    runtimeOnly(Dependencies.JWT_IMPL)
    runtimeOnly(Dependencies.JWT_JACKSON)

    // apache
    implementation(Dependencies.APACHE_POI)
    implementation(Dependencies.APACHE_POI_XML)
    implementation(Dependencies.COMMONS_IO)
    implementation(Dependencies.APACHE_TIKA)

    // querydsl
    implementation(Dependencies.QUERY_DSL)
    implementation(Dependencies.QUERY_DSL_APT)
    kapt(Dependencies.QUERY_DSL_APT)

    // prometheus
    implementation(Dependencies.PROMETHEUS_MICROMETER)
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}

idea {
    module {
        val kaptMain = file("build/generated/source/kapt/main")
        sourceDirs.add(kaptMain)
        generatedSourceDirs.add(kaptMain)
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

application {
    mainClass.set("com.msg.gauth.GauthBackendApplicationKt")
}

ktlint {
    verbose.set(true)
    android.set(false)
    outputToConsole.set(true)
    coloredOutput.set(false)
    ignoreFailures.set(false)
    enableExperimentalRules.set(false)

    disabledRules.set(setOf("no-wildcard-imports", "import-ordering"))
}