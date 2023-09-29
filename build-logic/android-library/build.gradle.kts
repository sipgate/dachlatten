plugins {
    `kotlin-dsl`
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("androidLibraryBase") {
            id = "android-library-base"
            implementationClass = "AndroidLibraryBasePlugin"
        }
        register("androidLibraryRelease") {
            id = "android-library-release"
            implementationClass = "AndroidLibraryReleasePlugin"
        }
    }
}