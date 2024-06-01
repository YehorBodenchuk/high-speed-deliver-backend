rootProject.name = "tpr-backend"
include("parcel-ms")
include("api-gateway-ms")
include("auth-ms")

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            version("spring", "3.2.4")
            version("lombok", "1.18.32")
            version("cloud", "4.1.3")
            version("junit", "5.11.0-M2")
            version("mockito", "5.12.0")
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
