plugins {
	id("webhooklogger.platform-conventions")
	alias(libs.plugins.pluginYml)
}

dependencies {
	compileOnly(libs.platform.paper)

	implementation(libs.bstats.bukkit)
}

bukkit {
	main = "fi.fabianadrian.webhooklogger.paper.WebhookLoggerPaper"
	name = rootProject.name
	apiVersion = "1.17"
	authors = listOf("FabianAdrian")
	website = "https://github.com/fabianmakila/WebhookLogger"
	softDepend = listOf("MiniPlaceholders")
	commands {
		register("webhooklogger") {
			description = "WebhookLogger main command"
			permission = "webhooklogger.reload"
		}
	}
	permissions {
		register("webhooklogger.reload") {
			description = "Allows you to run the reload command"
		}
	}
}