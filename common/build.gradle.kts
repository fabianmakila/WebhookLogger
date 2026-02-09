plugins {
	id("webhooklogger.java-conventions")
}

dependencies {
	// Webhook clients
	implementation(libs.jdwebhooks)
	implementation(libs.mcdiscordserializer) {
		exclude("net.kyori")
	}

	implementation(libs.cloud.minecraftExtras)

	// These are provided by the platforms
	compileOnly(libs.adventure)
	compileOnly(libs.adventure.text.minimessage)
	compileOnly(libs.adventure.text.serializer.ansi)
	compileOnly(libs.slf4j)
	compileOnly(libs.configurate)

	compileOnly(libs.plugin.miniplaceholders)
	compileOnly(libs.plugin.carbon)
}