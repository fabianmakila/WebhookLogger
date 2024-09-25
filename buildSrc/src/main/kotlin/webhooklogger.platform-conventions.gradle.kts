import org.gradle.kotlin.dsl.invoke
import org.gradle.kotlin.dsl.support.uppercaseFirstChar

plugins {
	id("webhooklogger.java-conventions")
	id("com.gradleup.shadow")
}

tasks {
	build {
		dependsOn(shadowJar)
	}
	shadowJar {
		minimize()

		destinationDirectory.set(file("${rootProject.rootDir}/dist"))
		archiveClassifier.set("")
		archiveBaseName.set("${rootProject.name}-${project.name.uppercaseFirstChar()}")

		sequenceOf(
			"io.github._4drian3d.jdwebhooks",
			"com.google.gson",
			"org.bstats",
			"org.json",
			"space.arim",
			"dev.vankka.mcdiscordreserializer",
			"org.incendo.cloud",
			"io.leangen"
		).forEach { pkg ->
			relocate(pkg, "fi.fabianadrian.webhooklogger.dependency.$pkg")
		}
	}
}