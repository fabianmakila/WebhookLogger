plugins {
    alias(libs.plugins.indra)
    alias(libs.plugins.minotaur)
    alias(libs.plugins.shadow)
    alias(libs.plugins.pluginYml)
    java
}

group = rootProject.group
version = rootProject.version
description = rootProject.description

dependencies {
    implementation(project(":common"))
    compileOnly(libs.platform.paper)

    implementation(libs.bstats.bukkit)
}

indra {
    javaVersions().target(17)
}

tasks {
    build {
        dependsOn(shadowJar)
    }
    shadowJar {
        minimize()
        sequenceOf(
            "io.github.4adrian3d",
            "com.google.code.gson",
            "org.bstats",
            "org.json",
            "space.arim"
        ).forEach { pkg ->
            relocate(pkg, "fi.fabianadrian.webhookchatlogger.dependency.$pkg")
        }
        destinationDirectory.set(file("${rootProject.rootDir}/dist"))
        archiveBaseName.set(rootProject.name + "-Paper")
        archiveAppendix.set("")
    }
}

bukkit {
    main = "fi.fabianadrian.webhookchatlogger.paper.WebhookChatLoggerPlugin"
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