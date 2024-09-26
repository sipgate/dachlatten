pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()

        google()
        maven("https://packages.jetbrains.team/maven/p/amper/amper")
        maven("https://www.jetbrains.com/intellij-repository/releases")
        maven("https://packages.jetbrains.team/maven/p/ij/intellij-dependencies")
    }
}

plugins {
    // apply the plugin:
    id("org.jetbrains.amper.settings.plugin").version("0.4.0")
}

rootProject.name = "Dachlatten"
//include(":dachlatten-android")
//include(":dachlatten-compose")
include(":dachlatten-datetime")
include(":dachlatten-debug")
include(":dachlatten-flow")
include(":dachlatten-google")
include(":dachlatten-markdown")
include(":dachlatten-primitives")
//include(":dachlatten-retrofit")
