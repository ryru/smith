plugins {
    id("java")
    id("application")
}

group = "ch.addere"
version = "1.0-SNAPSHOT"

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    implementation("org.javassist:javassist:3.30.2-GA")
    implementation("org.slf4j:slf4j-api:1.7.28")
    testImplementation(platform("org.junit:junit-bom:5.8.2"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}

// Requires this manifest and make a fat jar containing javaassist dependency in the jar included
tasks.withType<Jar> {
    manifest {
        attributes(
            mapOf(
                "Agent-Class" to "ch.addere.agent.Smith",
                "Can-Redefine-Classes" to "true",
                "Can-Retransform-Classes" to "true",
                "Premain-Class" to "ch.addere.agent.Smith"
            )
        )
    }

    val dependencies = configurations.runtimeClasspath.get().map(::zipTree)
    from(dependencies)
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}
