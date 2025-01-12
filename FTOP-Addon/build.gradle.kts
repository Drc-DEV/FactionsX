import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    java
    kotlin("jvm")
    id("com.github.johnrengelman.shadow")
}

dependencies {
    compileOnly(project(":FactionsX"))
    compileOnly(project(":AddonFramework"))
    compileOnly(project(":BasePlugin"))
    compileOnly("io.papermc:paperlib:1.0.7")
    compileOnly("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.6.10")
    compileOnly("org.spigotmc:spigot-api:1.18.2-R0.1-SNAPSHOT")
    compileOnly("com.github.MinusKube:SmartInvs:master-SNAPSHOT")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.0-native-mt")
    compileOnly("com.github.SavageLabs:JSONMessage:410f38c")
    compileOnly("me.clip:placeholderapi:2.10.10")
    compileOnly("com.github.cryptomorin:XSeries:8.7.0")
    compileOnly("com.github.OmerBenGera:WildStackerAPI:b19")
}


tasks {
    val shadowJar = named<ShadowJar>("shadowJar") {
        mergeServiceFiles()
        val shadePath = "net.prosavage.factionsx.shade"
        relocate("kotlinx", "$shadePath.ftop-addon.kotlinx")
        relocate("kotlin", "$shadePath.kotlin")
        relocate("javax.annotation", "$shadePath.annotations")
        relocate("com.cryptomorin.xseries", "$shadePath.xseries")
        relocate("fr.minuskube.inv", "$shadePath.smart-invs")
        relocate("io.papermc.lib", "$shadePath.paperlib")
        relocate("me.rayzr522.jsonmessage", "$shadePath.jsonmessage")
        minimize()
        archiveFileName.set("FTOP-Addon-${project.version}.jar")
    }
}