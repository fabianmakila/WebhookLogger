plugins {
    alias(libs.plugins.indra)
    alias(libs.plugins.shadow)
    alias(libs.plugins.pluginYml)
    java
}

group = rootProject.group
version = rootProject.version
description = rootProject.description

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.19.4-R0.1-SNAPSHOT")

    implementation(project(":common"))
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
            "club.minnced",
            "kotlin",
            "okhttp3",
            "okio",
            "org.json",
            "dev.vankka",
            "space.arim"
        ).forEach { pkg ->
            relocate(pkg, "fi.fabianadrian.webhookchatlogger.dependency.$pkg")
        }
        destinationDirectory.set(file("${rootProject.rootDir}/dist"))
        archiveBaseName.set(rootProject.name + "-Paper")
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