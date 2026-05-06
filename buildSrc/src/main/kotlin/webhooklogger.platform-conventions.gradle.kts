import org.gradle.kotlin.dsl.invoke
import org.gradle.kotlin.dsl.support.uppercaseFirstChar

plugins {
	id("webhooklogger.java-conventions")
	id("com.gradleup.shadow")
}

dependencies {
	implementation(project(":common"))
}

tasks {
	build {
		dependsOn(shadowJar)
	}
	shadowJar {
		destinationDirectory.set(rootProject.layout.buildDirectory.dir("libs"))
		archiveClassifier.set("")
		archiveBaseName.set("${rootProject.name}-${project.name.uppercaseFirstChar()}")
	}
}