rootProject.name = "tpr-backend"
include("parcel-ms")
include("api-gateway-ms")
include("auth-ms")
include("notify-ms")

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            version("spring", "3.2.4")
            version("lombok", "1.18.32")
            version("cloud", "4.1.3")
            version("junit", "5.11.0-M2")
            version("mockito", "5.12.0")
            version("crypto", "6.3.0")
            version("jwt", "0.12.5")
            version("jackson", "2.17.1")

            library("jackson", "com.fasterxml.jackson.core", "jackson-databind").versionRef("jackson")
            library("spring-thymeleaf", "org.springframework.boot", "spring-boot-starter-thymeleaf").versionRef("spring")
            library("spring-mail", "org.springframework.boot", "spring-boot-starter-mail").versionRef("spring")
            library("spring-rabbitmq", "org.springframework.boot", "spring-boot-starter-amqp").versionRef("spring")
            library("jwt-api", "io.jsonwebtoken", "jjwt-api").versionRef("jwt")
            library("jwt-impl", "io.jsonwebtoken", "jjwt-impl").versionRef("jwt")
            library("jwt-jackson", "io.jsonwebtoken", "jjwt-jackson").versionRef("jwt")
            library("spring-validation", "org.springframework.boot", "spring-boot-starter-validation").versionRef("spring")
            library("spring-security-crypto", "org.springframework.security", "spring-security-crypto").versionRef("crypto")
            library("spring-security", "org.springframework.boot", "spring-boot-starter-security").versionRef("spring")
            library("test-mockito-core", "org.mockito", "mockito-core").versionRef("mockito")
            library("test-junit-engine", "org.junit.jupiter", "junit-jupiter-engine").versionRef("junit")
            library("test-junit", "org.junit.jupiter", "junit-jupiter").versionRef("junit")
            library("test-junit-api", "org.junit.jupiter", "junit-jupiter-api").versionRef("junit")
            library("spring-boot", "org.springframework.boot", "spring-boot-starter-web").versionRef("spring")
            library("spring-mongodb", "org.springframework.boot", "spring-boot-starter-data-mongodb").versionRef("spring")
            library("spring-test", "org.springframework.boot", "spring-boot-starter-test").versionRef("spring")
            library("lombok", "org.projectlombok", "lombok").versionRef("lombok")
            library("spring-cloud", "org.springframework.cloud", "spring-cloud-starter-gateway").versionRef("cloud")
            library("spring-actuator", "org.springframework.boot", "spring-boot-starter-actuator").versionRef("spring")

            plugin("spring-boot-plugin", "org.springframework.boot").versionRef("spring")
        }
    }
}
