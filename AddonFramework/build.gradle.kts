plugins {
    java
}

dependencies {
    compileOnly("org.spigotmc:spigot-api:1.17-R0.1-SNAPSHOT")
    compileOnly(project(":BasePlugin"))
}

tasks {

    compileJava {
        sourceCompatibility = "1.8"
        targetCompatibility = "1.8"
    }

    getByName<Jar>("jar") {
        archiveFileName.set("AddonFramework-${project.version}.jar")
    }
}