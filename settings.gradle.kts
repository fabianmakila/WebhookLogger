rootProject.name = "WebhookChatLogger"

include("common")

sequenceOf(
    "paper"
).forEach { module ->
    include(":platform:$module")
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenCentral()
        maven("https://papermc.io/repo/repository/maven-public/")
    }
}
