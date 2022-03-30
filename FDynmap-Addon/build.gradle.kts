import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    java
    id("com.github.johnrengelman.shadow")
}

repositories {
    maven("https://repo.mikeprimm.com/")
}

dependencies {
    compileOnly(project(":FactionsX"))
    compileOnly(project(":AddonFramework"))
    compileOnly(project(":BasePlugin"))
    compileOnly("org.spigotmc:spigot-api:1.18.2-R0.1-SNAPSHOT")
    implementation("org.jetbrains:annotations:23.0.0")
    compileOnly("us.dynmap:dynmap-api:3.1") {
        exclude("org.bukkit")
        exclude("org.bstats")
        exclude("com.nijikokun.bukkit")
        exclude("de.bananaco")
        exclude("org.anjocaido")
        exclude("org.getspout")
        exclude("com.platymuus.bukkit.permissions")
        exclude("ru.tehkode")
    }
}

tasks {

    val shadowJar = named<ShadowJar>("shadowJar") {
        mergeServiceFiles()
        minimize()
        val shadePath = "net.prosavage.factionsx.shade"
        relocate("kotlin", "$shadePath.kotlin")
        relocate("org.jetbrains.annotations", "$shadePath.jetbrains-annotations")
        archiveFileName.set("FDynmap-Addon-${project.version}.jar")
    }
}