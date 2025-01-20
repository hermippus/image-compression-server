plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version "7.0.0"
}

group = "me.hermippus"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.tinylog:tinylog-api:2.7.0")
    implementation("org.tinylog:tinylog-impl:2.7.0")
}

tasks.withType<Jar> {
    manifest {
        attributes["Manifest-Version"] = "1.0"
        attributes["Main-Class"] = "me.hermippus.Main"
    }
}