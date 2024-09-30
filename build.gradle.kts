val mockkVersion: String by project

plugins {
    kotlin("jvm") version "2.0.10"
}

group = "ru.sug4chy"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("io.mockk:mockk:${mockkVersion}")
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}