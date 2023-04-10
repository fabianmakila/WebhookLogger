plugins {
    id("java")
    id("net.kyori.indra") version "3.0.1"
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("net.minecrell.plugin-yml.bukkit") version "0.5.3"
}

group = "fi.fabianadrian"
version = "1.2.1-SNAPSHOT"
description = "A simple plugin that forwards chat messages to a webhook."

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.19.4-R0.1-SNAPSHOT")

    implementation("club.minnced:discord-webhooks:0.8.2") {
        exclude("org.slf4j")
    }
    implementation("org.spongepowered:configurate-hocon:4.1.2")
    implementation("dev.vankka:mcdiscordreserializer:4.3.0") {
        exclude("net.kyori")
    }
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
        isEnableRelocation = true
        relocationPrefix = "fi.fabianadrian.webhookchatlogger.dependency"
    }
}

bukkit {
    main = "fi.fabianadrian.webhookchatlogger.WebhookChatLogger"
    apiVersion = "1.17"
    authors = listOf("FabianAdrian")
    website = "https://github.com/fabianmakila/WebhookChatLogger"
}