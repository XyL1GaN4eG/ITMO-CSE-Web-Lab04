plugins {
    id("java")
    id("io.freefair.lombok") version "8.10"
    id("war")
}

group = "web.lab04"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.postgresql:postgresql:42.7.4")
    implementation("org.mongodb:mongodb-driver-sync:5.2.1")
    compileOnly("org.projectlombok:lombok:1.18.36")

    implementation("jakarta.ws.rs:jakarta.ws.rs-api:3.1.0")
    implementation("jakarta.annotation:jakarta.annotation-api:3.0.0")
    implementation("jakarta.enterprise:jakarta.enterprise.cdi-api:4.1.0")
    implementation("jakarta.persistence:jakarta.persistence-api:3.2.0")
    implementation("jakarta.inject:jakarta.inject-api:2.0.1")
    implementation("jakarta.ejb:jakarta.ejb-api:4.0.1")


    // SLF4J для логирования
    implementation("org.slf4j:slf4j-api:2.0.16")
}



tasks.withType<War> {
    manifest {
        attributes["Main-Class"] = "Main"
    }

    from("src/main/webapp") {
        into("/")
    }

    val dependencies = configurations
        .runtimeClasspath
        .get()
        .map(::zipTree)
    from(dependencies)
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}

tasks.compileJava {
    options.encoding = "UTF-8"
}

tasks.javadoc {
    options.encoding = "UTF-8"
}

tasks.create("deploy") {
    dependsOn("war")

    doLast {
        exec {
            workingDir(".")
            commandLine("bash", "deploy.sh")
        }
    }
}