base {
    archivesBaseName = "bookstore-kotlin-jdk8"
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

/**
 * before maven-compiler-plugin
<plugin>
    <groupId>org.jetbrains.kotlin</groupId>
    <artifactId>kotlin-maven-plugin</artifactId>
    <version>1.5.31</version>
    <executions>
        <execution>
            <id>compile</id>
            <phase>process-sources</phase>
            <goals>
                <goal>compile</goal>
            </goals>
        </execution>
        <execution>
            <id>test-compile</id>
            <phase>process-test-sources</phase>
            <goals>
                <goal>test-compile</goal>
            </goals>
        </execution>
    </executions>
    <configuration>
        <args>
            <arg>-Xjsr305=strict</arg>
        </args>
        <compilerPlugins>
            <plugin>spring</plugin>
            <plugin>all-open</plugin>
        </compilerPlugins>
        <pluginOptions>
            <option>all-open:annotation=javax.persistence.*</option>
        </pluginOptions>
    </configuration>
    <dependencies>
        <dependency>
            <groupId>org.jetbrains.kotlin</groupId>
            <artifactId>kotlin-maven-allopen</artifactId>
            <version>1.5.31</version>
        </dependency>
        <dependency>
            <groupId>org.jetbrains.kotlin</groupId>
            <artifactId>kotlin-maven-noarg</artifactId>
            <version>1.5.31</version>
        </dependency>
    </dependencies>
</plugin>
 */
