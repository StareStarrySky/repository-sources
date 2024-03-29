import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import com.github.starestarrysky.extension.GitHubExtension
import com.github.starestarrysky.tasks.SiteTask

buildscript {
    repositories {
        mavenLocal()
        maven("https://plugins.gradle.org/m2/")
        mavenCentral()
    }

    val kotlinVersion = project.property("kotlin.version") as String
    val dokkaVersion = project.property("kotlin.dokka.version") as String
    val siteVersion = project.property("kotlin.site.version") as String

    val gradleSpringVersion = project.property("gradle.spring.dependency.version") as String

    dependencies {
        classpath(kotlin("allopen", kotlinVersion))
        classpath(kotlin("noarg", kotlinVersion))
        classpath(kotlin("gradle-plugin", kotlinVersion))
        classpath("org.jetbrains.dokka:dokka-gradle-plugin:${dokkaVersion}")
        classpath("com.github.starestarrysky:site-gradle-plugin:${siteVersion}")

        classpath("io.spring.gradle:dependency-management-plugin:${gradleSpringVersion}")
    }
}

apply(plugin = "com.github.starestarrysky.site-gradle-plugin")

allprojects {
    val revision = project.property("revision") as String
    val javaVersion = project.property("java.version") as String

    val springBootDepVer = project.property("spring.boot.dependencies.version") as String
    val springCloudDepVer = project.property("spring.cloud.dependencies.version") as String

    group = "xyz.starestarrysky.library"
    version = revision

    apply(plugin = "java-library")
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

    configure<JavaPluginExtension> {
        sourceCompatibility = JavaVersion.toVersion(javaVersion)
        targetCompatibility = JavaVersion.toVersion(javaVersion)
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
            jvmTarget = javaVersion
        }
    }
}

tasks.named<Wrapper>("wrapper") {
    gradleVersion = project.property("gradle.version") as String
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
