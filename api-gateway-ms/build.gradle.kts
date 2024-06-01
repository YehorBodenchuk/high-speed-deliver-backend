plugins {
    id("java")
    alias(libs.plugins.spring.boot.plugin)
}

group = "org.tpr.api"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.lombok)
    annotationProcessor(libs.lombok)
    implementation(libs.spring.actuator)
    implementation(libs.spring.security)
    implementation(libs.spring.cloud)
}
