plugins {
    id("java")
    alias(libs.plugins.spring.boot.plugin)
}

group = "org.tpr.notify"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.jwt.api)
    runtimeOnly(libs.jwt.impl)
    runtimeOnly(libs.jwt.jackson)
    implementation(libs.spring.validation)
    implementation(libs.spring.security.crypto)
    implementation(libs.spring.boot)
    implementation(libs.spring.security)
    implementation(libs.spring.actuator)
    implementation(libs.spring.mongodb)
    compileOnly(libs.lombok)
    annotationProcessor(libs.lombok)
    testImplementation(libs.spring.test)
    testImplementation(libs.test.junit)
    testImplementation(libs.test.junit.api)
    testImplementation(libs.test.mockito.core)
    testRuntimeOnly(libs.test.junit.engine)
}

tasks.test {
    useJUnitPlatform()
}