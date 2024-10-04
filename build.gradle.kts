val mockkVersion: String by project

plugins {
    kotlin("jvm") version "2.0.20"
}

group = "ru.sug4chy"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.github.ajalt.clikt:clikt:5.0.0")
    implementation("com.github.ajalt.clikt:clikt-markdown:5.0.0")

    testImplementation("io.mockk:mockk:${mockkVersion}")
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}