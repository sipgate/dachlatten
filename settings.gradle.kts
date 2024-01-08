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
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven("https://androidx.dev/storage/compose-compiler/repository")
    }
}

rootProject.name = "Dachlatten"
include(":dachlatten-android")
include(":dachlatten-compose")
include(":dachlatten-datetime")
include(":dachlatten-debug")
include(":dachlatten-flow")
include(":dachlatten-google")
include(":dachlatten-primitives")
include(":dachlatten-retrofit")
