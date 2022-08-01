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
                url = uri("${rootProject.buildDir}/deploy")
            }
        }
        publications {
            create<MavenPublication>("maven") {
//                artifact("*.jar")
            }
        }
    }
}
