import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    java
    kotlin("jvm")
    id("com.github.johnrengelman.shadow")
}

description = "Implement a virtual shared faction chest."

dependencies {
    compileOnly(project(":FactionsX")) {
        exclude("kotlin")
    }
    compileOnly(project(":AddonFramework"))
    compileOnly(project(":BasePlugin"))
    compileOnly("io.papermc:paperlib:1.0.7")
    compileOnly("org.spigotmc:spigot-api:1.18.2-R0.1-SNAPSHOT")
    compileOnly("com.github.cryptomorin:XSeries:8.7.0")
    compileOnly("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.6.10")
}


tasks {
    val shadowJar = named<ShadowJar>("shadowJar") {
        mergeServiceFiles()
        minimize()

        val shadePath = "net.prosavage.factionsx.shade"
        relocate("kotlin", "$shadePath.kotlin")
        relocate("io.papermc.lib", "$shadePath.paperlib")
        relocate("com.cryptomorin.xseries", "$shadePath.xseries")
        archiveFileName.set("FChest-Addon-${project.version}.jar")
    }
}