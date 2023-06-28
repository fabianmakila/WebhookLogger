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
    compileOnly("io.papermc.paper:paper-api:1.19.4-R0.1-SNAPSHOT")

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

modrinth {
    token.set(System.getenv("MODRINTH_TOKEN"))
    projectId.set("yOG0TUXA")
    uploadFile.set(tasks.shadowJar)
    gameVersions.addAll(
        listOf(
            "1.17", "1.17.1", "1.18", "1.18.1", "1.18.2", "1.19", "1.19.1", "1.19.2", "1.19.3", "1.19.4"
        )
    )
    loaders.add("paper") // Must also be an array - no need to specify this if you're using Loom or ForgeGradle
}