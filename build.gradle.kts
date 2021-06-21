import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
    repositories {
        mavenLocal()
        maven("https://plugins.gradle.org/m2/")
        mavenCentral()
        jcenter()
    }

    val kotlinVer = "1.4.32"
    val dokkaVer = "1.4.30"
    val siteVer = "1.0.2"

    val springDepVer = "1.0.11.RELEASE"

    dependencies {
        classpath(kotlin("allopen", kotlinVer))
        classpath(kotlin("noarg", kotlinVer))
        classpath(kotlin("gradle-plugin", kotlinVer))
        classpath("org.jetbrains.dokka:dokka-gradle-plugin:${dokkaVer}")
        classpath("com.github.starestarrysky:site-gradle-plugin:${siteVer}")

        classpath("io.spring.gradle:dependency-management-plugin:${springDepVer}")
    }
}

val revision = "1.0.0"
val javaVer = "15"
val gradleVer = "7.0"

val springBootDepVer = "2.4.5"
val springCloudDepVer = "Hoxton.SR10"

ext["commonsIoVer"] = "2.10.0"

allprojects {
    group = "xyz.starestarrysky.library"
    version = revision

    apply(plugin = "org.jetbrains.kotlin.jvm")

    repositories {
        mavenLocal()
        mavenCentral()
        jcenter()
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
