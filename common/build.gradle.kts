plugins {
    id("webhookchatlogger.java-conventions")
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
    compileOnly(libs.minimessage)
    compileOnly(libs.slf4j)
    compileOnly(libs.snakeyaml)

    compileOnly(libs.miniplaceholders)
}