import java.nio.charset.StandardCharsets

plugins {
	java
	id("com.diffplug.spotless")
}

group = rootProject.group
version = rootProject.version
description = rootProject.description

java.toolchain {
	languageVersion.set(JavaLanguageVersion.of(21))
}

tasks {
	build {
		dependsOn(spotlessApply)
	}
	compileJava {
		options.encoding = StandardCharsets.UTF_8.name()
	}
}

spotless {
	java {
		endWithNewline()
		formatAnnotations()
		leadingSpacesToTabs()
		removeUnusedImports()
		trimTrailingWhitespace()
	}
}