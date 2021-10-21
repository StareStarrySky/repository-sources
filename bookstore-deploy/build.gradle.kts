import com.github.starestarrysky.extension.GitHubExtension
import com.github.starestarrysky.tasks.SiteTask

plugins {
    `maven-publish`
    id("com.github.starestarrysky.site-gradle-plugin")
}

base {
    archivesBaseName = "bookstore-deploy"
}

configurations {
    "implementation" {
        exclude(group = "org.jetbrains.kotlin", module = "kotlin-reflect")
        exclude(group = "org.jetbrains.kotlin", module = "kotlin-stdlib")

        exclude(group = "org.springframework.boot", module = "spring-boot-dependencies")
        exclude(group = "org.springframework.cloud", module = "spring-cloud-dependencies")
    }

    "testImplementation" {
        exclude(group = "org.jetbrains.kotlin", module = "kotlin-test")
        exclude(group = "org.jetbrains.kotlin", module = "kotlin-test-junit")
    }
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
//                artifact("*.jar")
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
