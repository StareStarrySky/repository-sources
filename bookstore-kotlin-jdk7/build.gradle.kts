configurations {
    "implementation" {
        exclude(group = "org.springframework.boot", module = "spring-boot-dependencies")
        exclude(group = "org.springframework.cloud", module = "spring-cloud-dependencies")
    }
}

val kotlinVersion = project.property("kotlin.version") as String

dependencies {
    api(kotlin("stdlib-jdk7", kotlinVersion))
    testApi(kotlin("test", kotlinVersion))
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
                pom.withXml {
                    val dependencies = asNode().appendNode("dependencies")
                    configurations.api.get().allDependencies.forEach {
                        val dependency = dependencies.appendNode("dependency")
                        dependency.appendNode("groupId", it.group)
                        dependency.appendNode("artifactId", it.name)
                        dependency.appendNode("version", it.version)
//                        dependency.appendNode("type", "jar")
//                        dependency.appendNode("scope", "runtime")
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
