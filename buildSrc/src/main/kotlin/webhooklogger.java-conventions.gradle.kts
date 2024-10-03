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
		options.encoding = "UTF-8"
	}
}

spotless {
	java {
		endWithNewline()
		formatAnnotations()
		indentWithTabs()
		removeUnusedImports()
		trimTrailingWhitespace()
	}
}