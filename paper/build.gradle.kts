plugins {
    id("webhookchatlogger.platform-conventions")
    alias(libs.plugins.pluginYml)
}

dependencies {
    compileOnly(libs.platform.paper)

    implementation(libs.bstats.bukkit)
}

bukkit {
    main = "fi.fabianadrian.webhookchatlogger.paper.WebhookChatLoggerPaper"
    name = rootProject.name
    apiVersion = "1.17"
    authors = listOf("FabianAdrian")
    website = "https://github.com/fabianmakila/WebhookChatLogger"
    commands {
        register("webhookchatlogger") {
            aliases = listOf("wcl")
            description = "WebhookChatLogger main command"
            permission = "webhookchatlogger.reload"
        }
    }
    permissions {
        register("webhookchatlogger.reload") {
            description = "Allows you to run the reload command"
        }
    }
}