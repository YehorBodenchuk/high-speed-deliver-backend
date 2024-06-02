plugins {
    id("java")
    alias(libs.plugins.spring.boot.plugin)
}

group = "org.tpr.auth"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    // Spring boot dependencies
    implementation(libs.spring.validation)
    implementation(libs.spring.security.crypto)
    implementation(libs.spring.boot)
    implementation(libs.spring.security)
    implementation(libs.spring.actuator)
    implementation(libs.spring.mongodb)
    implementation(libs.spring.rabbitmq)

    // JWT libs
    implementation(libs.jwt.api)
    runtimeOnly(libs.jwt.impl)
    runtimeOnly(libs.jwt.jackson)

    // Lombok
    compileOnly(libs.lombok)
    annotationProcessor(libs.lombok)

    // Testing
    testImplementation(libs.spring.test)
    testImplementation(libs.test.junit)
    testImplementation(libs.test.junit.api)
    testImplementation(libs.test.mockito.core)
    testRuntimeOnly(libs.test.junit.engine)
}

tasks.test {
    useJUnitPlatform()
}