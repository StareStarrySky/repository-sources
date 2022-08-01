import org.jetbrains.dokka.gradle.DokkaTask

plugins {
    id("org.jetbrains.dokka")
}

val commonsIoVersion = project.property("commons-io.version") as String
val freemarkerVersion = project.property("freemarker.version") as String

dependencies {
    api("org.springframework:spring-beans")
    api("org.apache.commons:commons-lang3")
    api("commons-io:commons-io:${commonsIoVersion}")
    api("org.freemarker:freemarker:${freemarkerVersion}")
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
                url = uri("${rootProject.buildDir}/deploy")
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
