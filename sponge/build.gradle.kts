import org.spongepowered.gradle.plugin.config.PluginLoaders
import org.spongepowered.plugin.metadata.model.PluginDependency

plugins {
    id("webhooklogger.platform-conventions")
    alias(libs.plugins.sponge)
}

dependencies {
    implementation(project(":common"))
    compileOnly(libs.platform.sponge)

    implementation(libs.slf4j)
}

tasks {
    shadowJar {
        sequenceOf(
            "org.slf4j",
        ).forEach { pkg ->
            relocate(pkg, "fi.fabianadrian.webhooklogger.dependency.$pkg")
        }
    }
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
        description(rootProject.description)
        displayName(rootProject.name)
        entrypoint("fi.fabianadrian.webhooklogger.sponge.WebhookLoggerSponge")
        version(rootProject.version.toString())
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
        dependency("miniplaceholders") {
            loadOrder(PluginDependency.LoadOrder.AFTER)
            optional(true)
            version("2.2.4")
        }
    }
}