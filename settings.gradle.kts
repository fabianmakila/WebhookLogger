rootProject.name = "WebhookChatLogger"

sequenceOf(
    "common",
    "paper"
).forEach { include(it) }

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenCentral()
        maven("https://papermc.io/repo/repository/maven-public/")
    }
}
