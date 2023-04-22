plugins {
    alias(libs.plugins.indra)
    java
}

group = rootProject.group
version = rootProject.version
description = rootProject.description

dependencies {
    // Webhook clients
    implementation(libs.client.discord) {
        exclude("org.slf4j")
        exclude("org.jetbrains")
    }

    // Libraries
    implementation(libs.dazzleconf) {
        exclude("org.yaml")
    }
    implementation(libs.mcdiscordreserializer) {
        exclude("net.kyori")
    }

    // These are provided by the platforms
    compileOnly(libs.adventure)
    compileOnly(libs.slf4j)
}

indra {
    javaVersions().target(17)
}