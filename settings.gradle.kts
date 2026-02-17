pluginManagement {
    includeBuild("build-logic")
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Dachlatten"
include(":dachlatten-android")
include(":dachlatten-compose")
include(":dachlatten-datetime")
include(":dachlatten-datetime-android")
include(":dachlatten-debug")
include(":dachlatten-flow")
include(":dachlatten-google")
include(":dachlatten-io")
include(":dachlatten-markdown")
include(":dachlatten-primitives")
include(":dachlatten-retrofit")
