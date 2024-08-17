plugins {
    id("webhooklogger.java-conventions")
}

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
    compileOnly(libs.adventure.text.minimessage)
    compileOnly(libs.adventure.text.serializer.plain)
    compileOnly(libs.slf4j)
    compileOnly(libs.snakeyaml)

    compileOnly(libs.miniplaceholders)
}