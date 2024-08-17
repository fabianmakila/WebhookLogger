plugins {
	`kotlin-dsl`
}

dependencies() {
	implementation("com.diffplug.spotless:spotless-plugin-gradle:${libs.plugins.spotless.get().version}")
}