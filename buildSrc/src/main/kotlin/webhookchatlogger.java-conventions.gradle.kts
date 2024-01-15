plugins {
	`java-library`
	id("com.diffplug.spotless")
}

group = rootProject.group
version = rootProject.version
description = rootProject.description

java.toolchain {
	languageVersion.set(JavaLanguageVersion.of(17))
}

tasks {
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