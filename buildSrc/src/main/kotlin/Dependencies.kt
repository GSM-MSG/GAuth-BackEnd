object Dependencies {
    // kotlin
    const val KOTLIN_REFLET = "org.jetbrains.kotlin:kotlin-reflect"
    const val KOTLIN_JDK = "org.jetbrains.kotlin:kotlin-stdlib-jdk8"

    // spring
    const val SPRING_JPA = "org.springframework.boot:spring-boot-starter-data-jpa"
    const val SPRING_REDIS = "org.springframework.boot:spring-boot-starter-data-redis"
    const val SPRING_SECURITY = "org.springframework.boot:spring-boot-starter-security"
    const val SPRING_WEB = "org.springframework.boot:spring-boot-starter-web"
    const val SPRING_MAIL = "org.springframework.boot:spring-boot-starter-mail"
    const val SPRING_THYMELEAF = "org.springframework.boot:spring-boot-starter-thymeleaf"
    const val SPRING_VALIDATION = "org.springframework.boot:spring-boot-starter-validation"
    const val CONFIG_PROCESSOR = "org.springframework.boot:spring-boot-configuration-processor"
    const val SPRING_STARTER_TEST = "org.springframework.boot:spring-boot-starter-test"
    const val SPRING_SECURITY_TEST = "org.springframework.security:spring-security-test"
    const val SPRING_AOP = "org.springframework.boot:spring-boot-starter-aop"

    // jackson
    const val JACKSON_MODULE_KOTLIN = "com.fasterxml.jackson.module:jackson-module-kotlin"

    // aws
    const val AWS_S3 = "com.amazonaws:aws-java-sdk-s3:${DependencyVersions.AWS_S3_VERSION}"

    // database
    const val H2_DATABASE = "com.h2database:h2"
    const val MYSQL_DATABASE = "mysql:mysql-connector-java"
    const val MARIADB_DATABASE = "org.mariadb.jdbc:mariadb-java-client"

    // jwt
    const val JWT_API = "io.jsonwebtoken:jjwt-api:${DependencyVersions.JWT_API_VERSION}"
    const val JWT_IMPL = "io.jsonwebtoken:jjwt-impl:${DependencyVersions.JWT_IMPL_VERSION}"
    const val JWT_JACKSON= "io.jsonwebtoken:jjwt-jackson:${DependencyVersions.JWT_JACKSON_VERSION}"

    // apache
    const val APACHE_POI = "org.apache.poi:poi:${DependencyVersions.APACHE_POI_VERSION}"
    const val APACHE_POI_XML = "org.apache.poi:poi-ooxml:${DependencyVersions.APACHE_POI_XML_VERSION}"
    const val COMMONS_IO = "commons-io:commons-io:${DependencyVersions.COMMONS_IO_VERSION}"
    const val APACHE_TIKA = "org.apache.tika:tika-core:${DependencyVersions.APACHE_TIKA_VERSION}"

    // querydsl
    const val QUERY_DSL = "com.querydsl:querydsl-jpa:${DependencyVersions.QUERY_DSL_VERSION}"
    const val QUERY_DSL_APT = "com.querydsl:querydsl-apt:${DependencyVersions.QUERY_DSL_APT_VERSION}:jpa"

}