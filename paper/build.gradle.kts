plugins {
    id("webhookchatlogger.java-conventions")
    alias(libs.plugins.shadow)
    alias(libs.plugins.pluginYml)
}

dependencies {
    implementation(project(":common"))
    compileOnly(libs.platform.paper)

    implementation(libs.bstats.bukkit)
}

tasks {
    build {
        dependsOn(shadowJar)
    }
    shadowJar {
        minimize()
        sequenceOf(
            "io.github._4drian3d.jdwebhooks",
            "com.google.gson",
            "org.bstats",
            "org.json",
            "space.arim",
            "dev.vankka.mcdiscordreserializer"
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