plugins {
	id("webhooklogger.platform-conventions")
	alias(libs.plugins.pluginYml.paper)
	alias(libs.plugins.runPaper)
}

dependencies {
	implementation(project(":common"))
	compileOnly(libs.platform.paper)

	implementation(libs.cloud.paper)
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
	runServer {
		downloadPlugins {
			modrinth("miniplaceholders", "J2guR3GH")
		}
		minecraftVersion("1.20.6")
	}
}