import xyz.jpenilla.resourcefactory.paper.PaperPluginYaml.Load

plugins {
	id("webhooklogger.platform-conventions")
	alias(libs.plugins.resourceFactory.paper)
	alias(libs.plugins.runPaper)
}

dependencies {
	compileOnly(libs.platform.paper)

	implementation(libs.cloud.paper)
	implementation(libs.bstats.bukkit)
	implementation(libs.configurate) {
		exclude("org.yaml")
	}
}

paperPluginYaml {
	main = "fi.fabianadrian.webhooklogger.paper.WebhookLoggerPaper"
	name = rootProject.name
	apiVersion = "1.21.4"
	authors = listOf("FabianAdrian")
	website = "https://github.com/fabianmakila/webhooklogger"

	permissions {
		register("webhooklogger.command.reload") {
			description = "Allows you to run the reload command"
		}
	}

	dependencies {
		server {
			register("MiniPlaceholders") {
				required = false
				load = Load.BEFORE
			}
			register("CarbonChat") {
				required = false
				load = Load.BEFORE
			}
		}
	}
}

tasks {
	runServer {
		downloadPlugins {
			modrinth("miniplaceholders", "7caNTwMh")
		}
		minecraftVersion("1.21.4")
	}
	shadowJar {
		sequenceOf(
			"org.spongepowered.configurate",
			"io.leangen.geantyref"
		).forEach { pkg ->
			relocate(pkg, "fi.fabianadrian.webhooklogger.dependency.$pkg")
		}
	}
}