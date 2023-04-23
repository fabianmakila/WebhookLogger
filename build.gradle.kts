plugins {
    alias(libs.plugins.minotaur)
    java
}

group = "fi.fabianadrian"
version = "1.4.0"
description = "A simple plugin that forwards chat messages to a webhook."

modrinth {
    token.set(System.getenv("MODRINTH_TOKEN"))
    projectId.set("yOG0TUXA")
    //uploadFile.set(tasks.jar)
    gameVersions.addAll(
        listOf(
            "1.17", "1.17.1", "1.18", "1.18.1", "1.18.2", "1.19", "1.19.1", "1.19.2", "1.19.3", "1.19.4"
        )
    )
    loaders.add("paper") // Must also be an array - no need to specify this if you're using Loom or ForgeGradle
}