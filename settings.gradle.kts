rootProject.name = "WebhookLogger"

sequenceOf(
	"common",
	"paper",
	"sponge"
).forEach { include(it) }

dependencyResolutionManagement {
	repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
	repositories {
		mavenCentral()
		maven("https://repo.papermc.io/repository/maven-public/")
		maven("https://repo.spongepowered.org/repository/maven-public/")
		maven("https://s01.oss.sonatype.org/content/repositories/snapshots/") {
			name = "sonatype-snapshots"
			mavenContent {
				snapshotsOnly()
			}
		}
	}
}
