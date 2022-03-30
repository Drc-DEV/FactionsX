plugins {
    java
}

dependencies {
    compileOnly("org.spigotmc:spigot-api:1.18.2-R0.1-SNAPSHOT")
    compileOnly(project(":BasePlugin"))
}

tasks {

    getByName<Jar>("jar") {
        archiveFileName.set("AddonFramework-${project.version}.jar")
    }
}