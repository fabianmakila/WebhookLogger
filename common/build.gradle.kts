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

    }

    // Libraries
    implementation(libs.dazzleconf) {
        exclude("org.yaml")
    }

    // These are provided by the platforms
    compileOnly(libs.adventure)
    compileOnly(libs.slf4j)
    compileOnly(libs.snakeyaml)
}

indra {
    javaVersions().target(17)
}