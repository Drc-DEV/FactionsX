import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.apache.tools.ant.filters.ReplaceTokens

plugins {
    java
    kotlin("jvm")
    id("com.github.johnrengelman.shadow")
}

description = "FactionsX Core Module."

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.0-native-mt")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.6.10")
    implementation(project(":AddonFramework"))
    implementation(project(":BasePlugin"))
    //implementation("me.rayzr522:jsonmessage:1.2.2")
    implementation("me.rayzr522:jsonmessage:1.3.0")
    implementation("com.google.code.gson:gson:2.9.0")
    implementation("com.github.cryptomorin:XSeries:8.7.0")
    //implementation("org.bstats:bstats-bukkit:3.0.0")
    implementation("io.papermc:paperlib:1.0.8-SNAPSHOT")
    implementation("fr.mrmicky:FastParticles:v2.0.0")
    implementation("com.github.MinusKube:SmartInvs:master-SNAPSHOT")
    implementation("com.github.officialrarlab:FastBoard:b6887c9a5f")
    implementation(files("./lib/WorldGuardWrapper-1.0.1.jar"))
    //implementation("com.github.officialrarlab:WorldGuardWrapper:1.0")
    compileOnly("com.github.MilkBowl:VaultAPI:1.7")
    compileOnly("net.ess3:EssentialsX:2.17.2")
    compileOnly("org.spigotmc:spigot-api:1.18.2-R0.1-SNAPSHOT")
    compileOnly("me.clip:placeholderapi:2.10.10")
    compileOnly("com.github.brcdev-minecraft:shopgui-api:1.5.0")
    compileOnly("com.github.MyzelYam:SuperVanish:6.2.0") {
        exclude("be.maximvdw")
        exclude("com.comphenix.protocol")
        exclude("net.citizensnpcs")
    }
    compileOnly("com.sk89q.worldguard:worldguard-bukkit:7.0.0")
}


tasks {

    jar {
        archiveFileName.set("${project.name}-lib.jar")
    }

    processResources {
        filter<ReplaceTokens>(
                "tokens" to mapOf(
                        "project.version" to project.version
                )
        )
    }

    val shadowJar = named<ShadowJar>("shadowJar") {
        mergeServiceFiles()
        minimize()

        val shadePath = "net.prosavage.factionsx.shade"
        relocate("kotlinx", "$shadePath.kotlinx")
        relocate("kotlin", "$shadePath.kotlin")
        relocate("javax.annotation", "$shadePath.annotations")
        relocate("com.google.gson", "$shadePath.gson")
        relocate("com.cryptomorin.xseries", "$shadePath.xseries")
        relocate("fr.minuskube.inv", "$shadePath.smart-invs")
        relocate("fr.mrmicky.fastboard", "$shadePath.fastboard")
        relocate("fr.mrmicky.fastparticle", "$shadePath.fastparticle")
        relocate("io.papermc.lib", "$shadePath.paperlib")
        relocate("me.rayzr522.jsonmessage", "$shadePath.jsonmessage")
        relocate("org.bstats.bukkit", "$shadePath.bstats-bukkit")
        relocate("org.jetbrains.annotations", "$shadePath.jetbrains-annotations")
        archiveFileName.set("${project.name}-${project.version}.jar")
    }
}