plugins {
    alias(libs.plugins.indra)
    java
}

group = rootProject.group
version = rootProject.version
description = rootProject.description

dependencies {
    // Webhook clients
    implementation(libs.client.discord)

    // External libraries
    implementation(libs.dazzleconf) {
        exclude("org.yaml")
    }
    implementation(libs.mcdiscordserializer) {
        exclude("net.kyori")
    }

    // These are provided by the platforms
    compileOnly(libs.adventure)
    compileOnly(libs.minimessage)
    compileOnly(libs.slf4j)
    compileOnly(libs.snakeyaml)

    compileOnly(libs.miniplaceholders)
}

indra {
    javaVersions().target(17)
}