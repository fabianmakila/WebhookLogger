import org.spongepowered.gradle.plugin.config.PluginLoaders
import org.spongepowered.plugin.metadata.model.PluginDependency

plugins {
    id("webhooklogger.java-conventions")
    alias(libs.plugins.sponge)
}

dependencies {
    implementation(project(":common"))
    compileOnly(libs.platform.sponge)

    implementation(libs.slf4j)
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
        entrypoint("fi.fabianadrian.webhooklogger.sponge.WebhookLoggerSponge")
        links {
            homepage("https://modrinth.com/plugin/webhooklogger")
            source("https://github.com/fabianmakila/webhooklogger")
            issues("https://github.com/fabianmakila/webhooklogger/issues")
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