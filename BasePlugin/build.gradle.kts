import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    java
    id("com.github.johnrengelman.shadow")
}

dependencies {
    compileOnly("org.spigotmc:spigot-api:1.17-R0.1-SNAPSHOT")

    implementation("com.github.cryptomorin:XSeries:8.4.0")
    implementation("fr.mrmicky:FastParticles:v2.0.0")
    implementation("org.apache.commons:commons-lang3:3.9")
    implementation("com.google.code.findbugs:jsr305:3.0.2")
    implementation("com.google.code.gson:gson:2.8.6")
}

tasks {

    compileJava {
        sourceCompatibility = "1.8"
        targetCompatibility = "1.8"
    }

    getByName<Jar>("jar") {
        archiveFileName.set("BasePlugin-${project.version}.jar")
    }

    val shadowJar = named<ShadowJar>("shadowJar") {
        mergeServiceFiles()
        minimize()
        val shadePath = "net.prosavage.baseplugin.shade"
        relocate("com.cryptomorin.xseries", "$shadePath.xseries")
        relocate("fr.mrmicky.fastparticle", "$shadePath.fastparticle")
        relocate("org.apache.commons", "$shadePath.apache.commons")
        relocate("com.google.gson", "$shadePath.gson")
        relocate("com.google.code.findbugs", "$shadePath.findbugs")
        relocate("javax.annotation", "$shadePath.annotations")
        archiveFileName.set("${project.name}-${project.version}.jar")
    }
}