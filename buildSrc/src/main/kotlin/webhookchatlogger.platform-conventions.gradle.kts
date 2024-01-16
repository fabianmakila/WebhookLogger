plugins {
	id("webhookchatlogger.java-conventions")
	id("com.github.johnrengelman.shadow")
}

dependencies {
	implementation(project(":common"))
}

tasks {
	build {
		dependsOn(shadowJar)
	}
	shadowJar {
		minimize()

		destinationDirectory.set(file("${rootProject.rootDir}/dist"))
		archiveClassifier.set("")
		archiveBaseName.set("${rootProject.name}-${project.name.replaceFirstChar(Char::titlecase)}")

		sequenceOf(
			"io.github._4drian3d.jdwebhooks",
			"com.google.gson",
			"org.bstats",
			"org.json",
			"space.arim",
			"dev.vankka.mcdiscordreserializer"
		).forEach { pkg ->
			relocate(pkg, "fi.fabianadrian.webhookchatlogger.dependency.$pkg")
		}
	}
}