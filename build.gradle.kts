plugins {
    id ("java")
}

group = "dev.highlands"
version = "1.0"

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}

repositories {
    mavenCentral()
    maven { url = uri("https://repo.papermc.io/repository/maven-public/") }
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.20.4-R0.1-SNAPSHOT")
}

tasks {
    assemble {
        dependsOn("sourcesJar")
    }

    register<Jar>("sourcesJar") {
        from(sourceSets["main"].allJava)
        archiveClassifier.set("sources")
    }
    jar {
        val paths = listOf(
            "C:\\Users\\Faceless\\Desktop\\Servers\\Purpur 1.20.4\\plugins",
            "C:\\Users\\kaden\\OneDrive\\Documents\\[1] My Files\\Development\\Test Server (Paper)\\plugins"
        )
        paths.forEach { path ->
            if (file(path).exists()) {
                destinationDirectory.set(file(path))
            }
        }
    }

    withType(JavaCompile::class.java) {
        options.encoding = "UTF-8"
    }
}