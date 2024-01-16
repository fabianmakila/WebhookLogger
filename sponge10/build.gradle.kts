import org.spongepowered.gradle.plugin.config.PluginLoaders
import org.spongepowered.plugin.metadata.model.PluginDependency

plugins {
    id("webhookchatlogger.platform-conventions")
    alias(libs.plugins.sponge)
}

dependencies {
    compileOnly(libs.platform.sponge10)
}

sponge {
    injectRepositories(false)

    apiVersion("10.0.0")
    loader {
        name(PluginLoaders.JAVA_PLAIN)
        version("1.0")
    }
    license("GPLv3")
    plugin(rootProject.name.lowercase()) {
        displayName(rootProject.name)
        version(rootProject.version.toString())
        entrypoint("fi.fabianadrian.webhookchatlogger.sponge10.WebhookChatLoggerSponge10")
        links {
            homepage("https://modrinth.com/plugin/webhookchatlogger")
            source("https://github.com/fabianmakila/webhookchatlogger")
            issues("https://github.com/fabianmakila/webhookchatlogger/issues")
        }
        contributor("FabianAdrian") {
            description("Developer")
        }
        dependency("spongeapi") {
            loadOrder(PluginDependency.LoadOrder.AFTER)
            optional(false)
        }
    }
}