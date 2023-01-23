plugins {
    id("java")
    id("net.kyori.indra") version "3.0.1"
    id("com.github.johnrengelman.shadow") version "7.1.2"
    id("net.minecrell.plugin-yml.bukkit") version "0.5.2"
}

group = "fi.fabianadrian"
version = "1.0.0"
description = "A simple plugin that forwards chat messages to a webhook."

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.19.3-R0.1-SNAPSHOT")

    implementation("club.minnced:discord-webhooks:0.8.2")
    implementation("org.spongepowered:configurate-hocon:4.1.2")
}

indra {
    javaVersions().target(17)
}

tasks {
    shadowJar {
        minimize()
        sequenceOf(
            "club.minnced.discord.webhook",
            "org.spongepowered.configurate"
        ).forEach { pkg ->
            relocate(pkg, "${group}.${rootProject.name.toLowerCase()}.lib.$pkg")
        }
    }
    build {
        dependsOn(shadowJar)
    }
}

bukkit {
    main = "fi.fabianadrian.webhookchatlogger.WebhookChatLogger"
    apiVersion = "1.17"
    authors = listOf("FabianAdrian")
    website = "https://github.com/fabianmakila/WebhookChatLogger"
}