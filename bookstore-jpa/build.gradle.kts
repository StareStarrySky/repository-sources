import com.github.starestarrysky.extension.GitHubExtension
import com.github.starestarrysky.tasks.SiteTask
import org.jetbrains.dokka.gradle.DokkaTask

plugins {
    kotlin("jvm")
    `maven-publish`
    id("org.jetbrains.dokka")
    id("com.github.starestarrysky.site-gradle-plugin")
}

base {
    archivesBaseName = "bookstore-jpa"
}

dependencies {
    api("javax.persistence:javax.persistence-api")
}

val generateSourcesJar by tasks.creating(Jar::class) {
    archiveClassifier.set("sources")
    from(sourceSets.main.get().java.srcDirs)
}

tasks.named<DokkaTask>("dokkaJavadoc") {
    outputDirectory.set(file("$buildDir/javadoc"))
}

val generateJavadoc by tasks.creating(Jar::class) {
    dependsOn("dokkaJavadoc")
    group = "jar"
    archiveClassifier.set("javadoc")
    from(tasks["dokkaJavadoc"].property("outputDirectory"))
}

tasks {
    publishing {
        repositories {
            maven {
                name = "projectDeploy"
                url = uri("${project.buildDir}/deploy")
            }
        }
        publications {
            create<MavenPublication>("maven") {
                artifact(generateJavadoc)
                artifact(generateSourcesJar)

                from(getComponents()["java"])
            }
        }
    }
}

configure<GitHubExtension> {
    credentials {
        oauthToken = ""
    }
}

val site by tasks.creating(SiteTask::class) {
    dependsOn("publishMavenPublicationToProjectDeployRepository")
    repositoryName.set("repository")
    repositoryOwner.set("StareStarrySky")
    branch.set("refs/heads/master")
    message.set("Repository for ${rootProject.version}.")
    outputDirectory.set(file("$buildDir/deploy"))
    includes.add("**/*")
}
