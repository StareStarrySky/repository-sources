import com.github.starestarrysky.extension.GitHubExtension
import com.github.starestarrysky.tasks.SiteTask
import org.jetbrains.dokka.gradle.DokkaTask

plugins {
    kotlin("jvm")
    id("maven-publish")
    id("org.jetbrains.dokka")
    id("com.github.starestarrysky.site-gradle-plugin")
}

base {
    archivesBaseName = "bookstore-base"
}

dependencies {
    implementation("org.springframework:spring-beans")
    implementation("org.springframework:spring-context")
}

val generateSourcesJar by tasks.creating(Jar::class) {
    archiveClassifier.set("sources")
    from(sourceSets.main.get().java.srcDirs)
}

tasks.named<DokkaTask>("dokkaJavadoc") {
    outputDirectory.set(file("$buildDir${File.separator}javadoc"))
}

val generateJavadoc by tasks.creating(Jar::class) {
    dependsOn("dokkaJavadoc")
    group = "jar"
    archiveClassifier.set("javadoc")
    from(tasks["dokkaJavadoc"].property("outputDirectory"))
}

configure<GitHubExtension> {
    credentials {
        oauthToken = ""
    }
}

val site by tasks.creating(SiteTask::class) {
    repositoryName.set("repository-test")
    repositoryOwner.set("StareStarrySky")
    branch.set("refs/heads/main")
    message.set("Repository-test for ${rootProject.version}.")
    outputDirectory.set(file("${project.buildDir}/deploy"))
    includes.add("**/*")
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
