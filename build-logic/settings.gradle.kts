dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }

    versionCatalogs {
        create("libs") {
            from(files("../gradle/libs.versions.toml"))

            library("robolectric", "org.robolectric", "robolectric")
                .versionRef("robolectric")
        }
    }
}

rootProject.name = "build-logic"
include(":android-library")