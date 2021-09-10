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
    archivesBaseName = "bookstore-kotlin"
}

configurations {
    "implementation" {
        exclude(group = "org.springframework.boot", module = "spring-boot-dependencies")
        exclude(group = "org.springframework.cloud", module = "spring-cloud-dependencies")
    }
}

dependencies {
    api(kotlin("stdlib-jdk8", rootProject.ext["kotlinVer"].toString()))
    testApi(kotlin("test", rootProject.ext["kotlinVer"].toString()))
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
                pom.withXml {
                    val dependencies = asNode().appendNode("dependencies")
                    configurations.api.get().allDependencies.forEach {
                        val dependency = dependencies.appendNode("dependency")
                        dependency.appendNode("groupId", it.group)
                        dependency.appendNode("artifactId", it.name)
                        dependency.appendNode("version", it.version)
//                        dependency.appendNode("type", "jar")
                        dependency.appendNode("scope", "runtime")
                    }
                    configurations.testApi.get().allDependencies.forEach {
                        val dependency = dependencies.appendNode("dependency")
                        dependency.appendNode("groupId", it.group)
                        dependency.appendNode("artifactId", it.name)
                        dependency.appendNode("version", it.version)
//                        dependency.appendNode("type", "jar")
                        dependency.appendNode("scope", "test")
                    }
                }
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
