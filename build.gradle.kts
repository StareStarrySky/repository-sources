import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import com.github.starestarrysky.extension.GitHubExtension
import com.github.starestarrysky.tasks.SiteTask

buildscript {
    repositories {
        mavenLocal()
        maven("https://plugins.gradle.org/m2/")
        mavenCentral()
    }

    val kotlinVer = "1.5.31"

    dependencies {
        classpath(kotlin("allopen", kotlinVer))
        classpath(kotlin("noarg", kotlinVer))
        classpath(kotlin("gradle-plugin", kotlinVer))
        classpath("org.jetbrains.dokka:dokka-gradle-plugin:1.4.30")
        classpath("com.github.starestarrysky:site-gradle-plugin:1.0.2")

        classpath("io.spring.gradle:dependency-management-plugin:1.0.11.RELEASE")
    }
}

val revision = "1.0.0"
val javaVer = "15"
val gradleVer = "7.0"

val springBootDepVer = "2.4.5"
val springCloudDepVer = "Hoxton.SR10"

ext["commonsIoVer"] = "2.10.0"
ext["kotlinVer"] = "1.5.31"

apply(plugin = "com.github.starestarrysky.site-gradle-plugin")

allprojects {
    group = "xyz.starestarrysky.library"
    version = revision

    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "maven-publish")

    repositories {
        mavenLocal()
        maven("https://plugins.gradle.org/m2/")
        mavenCentral()
    }

    dependencies {
        val implementation by configurations
        val testImplementation by configurations

        implementation(kotlin("reflect"))
        implementation(kotlin("stdlib"))
        testImplementation(kotlin("test"))
        testImplementation(kotlin("test-junit"))

        implementation(platform("org.springframework.boot:spring-boot-dependencies:${springBootDepVer}"))
        implementation(platform("org.springframework.cloud:spring-cloud-dependencies:${springCloudDepVer}"))
    }

    configure<SourceSetContainer> {
        named("main") {
            java.srcDir("src/main/kotlin")
        }
        named("test") {
            java.srcDir("src/test/kotlin")
        }
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = javaVer
        }
    }
}

tasks.named<Wrapper>("wrapper") {
    gradleVersion = gradleVer
    distributionType = Wrapper.DistributionType.BIN
}

configure<GitHubExtension> {
    credentials.oauthToken = ""
}

tasks.register<SiteTask>("site") {
    subprojects.forEach { project ->
        dependsOn(":" + project.name + ":publishMavenPublicationToProjectDeployRepository")
    }

    repositoryName.set("repository")
    repositoryOwner.set("StareStarrySky")
    branch.set("refs/heads/v${rootProject.version}")
    message.set("Repository for ${rootProject.version}.")
    outputDirectory.set(file("${rootProject.buildDir}/deploy"))
    includes.add("**/*")
}
