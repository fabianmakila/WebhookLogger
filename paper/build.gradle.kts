plugins {
	id("webhooklogger.java-conventions")
	alias(libs.plugins.pluginYml.paper)
	alias(libs.plugins.shadow)
}

dependencies {
	implementation(project(":common"))
	compileOnly(libs.platform.paper)

	implementation(libs.bstats.bukkit)
}

paper {
	main = "fi.fabianadrian.webhooklogger.paper.WebhookLoggerPaper"
	name = rootProject.name
	apiVersion = "1.20.6"
	authors = listOf("FabianAdrian")
	website = "https://github.com/fabianmakila/WebhookLogger"

	permissions {
		register("webhooklogger.reload") {
			description = "Allows you to run the reload command"
		}
	}
}

tasks {
	build {
		dependsOn(shadowJar)
	}
	shadowJar {
		minimize()

		destinationDirectory.set(file("${rootProject.rootDir}/dist"))
		archiveClassifier.set("")
		archiveBaseName.set("${rootProject.name}-Paper")

		sequenceOf(
			"io.github._4drian3d.jdwebhooks",
			"com.google.gson",
			"org.bstats",
			"org.json",
			"space.arim",
			"dev.vankka.mcdiscordreserializer"
		).forEach { pkg ->
			relocate(pkg, "fi.fabianadrian.webhooklogger.dependency.$pkg")
		}
	}
}