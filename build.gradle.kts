import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
    repositories {
        mavenLocal()
        maven("https://plugins.gradle.org/m2/")
        mavenCentral()
    }

    val kotlinVersion = "1.4.32"

    dependencies {
        classpath(kotlin("allopen", kotlinVersion))
        classpath(kotlin("noarg", kotlinVersion))
        classpath(kotlin("gradle-plugin", kotlinVersion))
    }
}

allprojects {
    group = "xyz.starestarrysky.repository"
    version = "1.0.0"

    apply(plugin = "org.jetbrains.kotlin.jvm")

    repositories {
        mavenLocal()
        mavenCentral()
    }

    dependencies {
        val implementation by configurations
        val testImplementation by configurations

        implementation((kotlin("reflect")))
        implementation((kotlin("stdlib")))
        testImplementation((kotlin("test")))
        testImplementation((kotlin("test-junit")))
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
            jvmTarget = "15"
        }
    }
}

tasks.named<Wrapper>("wrapper") {
    gradleVersion = "7.0.0"
    distributionType = Wrapper.DistributionType.BIN
}
