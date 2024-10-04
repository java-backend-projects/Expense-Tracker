val mockkVersion: String by project

plugins {
    application
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

application {
    mainClass = "ru.sug4chy.Application"
}

tasks.jar {
    manifest {
        attributes("Main-Class" to "ru.sug4chy.ApplicationKt")
    }
    from(sourceSets.main.get().allSource)
}

tasks.withType<Jar> {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) })
}

tasks.test {
    useJUnitPlatform()
}