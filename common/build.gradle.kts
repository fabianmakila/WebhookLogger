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

	implementation(libs.cloud.minecraftExtras)

	// These are provided by the platforms
	compileOnly(libs.adventure)
	compileOnly(libs.adventure.text.minimessage)
	compileOnly(libs.adventure.text.serializer.ansi)
	compileOnly(libs.slf4j)
	compileOnly(libs.snakeyaml)

	compileOnly(libs.miniplaceholders)
}