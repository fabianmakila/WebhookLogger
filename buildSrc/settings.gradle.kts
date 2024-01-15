rootProject.name = "webhookchatlogger-conventions"

dependencyResolutionManagement {
	repositories {
		mavenCentral()
		gradlePluginPortal()
	}
	versionCatalogs {
		register("libs") {
			from(files("../gradle/libs.versions.toml")) // include from parent project
		}
	}
}