plugins {
    id("java")
    id("net.kyori.indra") version "3.0.1"
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("net.minecrell.plugin-yml.bukkit") version "0.5.3"
}

group = "fi.fabianadrian"
version = "1.3.0"
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
    implementation("space.arim.dazzleconf:dazzleconf-ext-snakeyaml:1.3.0-M1") {
        exclude("org.yaml")
    }
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