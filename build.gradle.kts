import org.gradle.kotlin.dsl.support.zipTo

allprojects {
    group = "net.prosavage.factionsx"
    version = "1.2-STABLE"
}

plugins {
    java
    kotlin("jvm") version "1.6.10"
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

val serverPluginDirectory: String by project


tasks {

    val copyToRoot = register("copyToRoot") {
        doLast {
            // Copy all our stuff to root project libs.
            for (subproject in subprojects.filter { project -> project.name != "AddonFramework" }) {
                copy {
                    from("${subproject.buildDir}/libs/")
                    into("${buildDir}/libs/")
                    include("${subproject.name}-${version}.jar")
                }
            }
        }
    }


    register("packageRelease") {
        dependsOn(copyToRoot)
        doLast {
            val releasePath = "${buildDir}/release/FactionsX+Addons"
            mkdir(releasePath)
            // Copy the plugins.
            copy {
                from("$buildDir/libs/")
                into(releasePath)
                include("*.jar")
                exclude("${project.name}-$version-all.jar", "*-Addon-$version.jar")
            }

            // Copy the addons.
            copy {
                from("$buildDir/libs/")
                into("$releasePath/addons")
                include("*-Addon-$version.jar")
                exclude("${project.name}-$version-all.jar")
            }


            val readme = file("$releasePath/README.txt")
            readme.writeText(
                "Hi, thanks for reading!\n" +
                        "\n" +
                        "The FactionsX-${project.version} is an open-source minecraft plugin, goes in the \"/plugins/\" folder.\n" +
                        "All jar files in the \"Addons\" folder, go in the \"/plugins/FactionsX/addons/\" folder.\n" +
                        "* F CropUpgrades Addon is 1.13+.\n" +
                        "\n" +
                        "The FactionsUUIDAPIProxy attempts to imitate FactionsUUID being present on your server, and proxies the API calls to FactionsX,\n" +
                        "this allows plugins using FactionsUUID's API to work with FactionsX. This is a very new addition and I dont expect it to work perfectly\n" +
                        "as it IS a hack. If you have issues with a plugin feel free to come to our discord for support.\n" +
                        "* FactionsUUIDAPIProxy is a minecraft plugin and goes in \"/plugins/\".\n" +
                        "\n" +
                        "\n" +
                        "Basic documentation for permissions or commands specific to this build can be found in the \"/plugins/FactionsX/docs\" " +
                        "folder after the first time starting up\n" +
                        "\n" +
                        "GitHub: https://github.com/ProSavage/FactionsX\n" +
                        "Wiki: https://wiki.savagelabs.net"
            )
            zipTo(File("${buildDir}/release/FactionsX-Release-${project.version}.zip"), File(releasePath))
        }
    }
}

subprojects {


    repositories {
        mavenLocal()
        mavenCentral()

        maven("https://repo.maven.apache.org/maven2")

        maven("https://hub.spigotmc.org/nexus/content/groups/public/")

        maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots")

        maven("https://repo.dmulloy2.net/nexus/repository/public/")
        maven("https://oss.sonatype.org/content/groups/public/")

        maven("https://repo.extendedclip.com/content/repositories/placeholderapi/")

        maven("https://repo.codemc.org/repository/maven-public")

        maven("https://papermc.io/repo/repository/maven-public/")


        maven("https://ci.ender.zone/plugin/repository/everything/")

        maven("https://rayzr.dev/repo/")

        maven("https://maven.enginehub.org/repo/")

        maven("https://jcenter.bintray.com/")

        maven("https://repo.extendedclip.com/content/repositories/placeholderapi/")

        maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
        maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
        maven("https://papermc.io/repo/repository/maven-releases/")
        maven("https://ci.ender.zone/plugin/repository/everything/")
        maven("https://minevolt.net/repo/")
        maven("https://repo.citizensnpcs.co/")

        maven("https://nexus.sirblobman.xyz/repository/public/")

        maven("https://jitpack.io")
    }
}

repositories {
    mavenCentral()
    mavenLocal()
}