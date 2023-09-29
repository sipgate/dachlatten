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
        register("androidLibraryTest") {
            id = "android-library-test"
            implementationClass = "AndroidLibraryTestPlugin"
        }
        register("androidLibraryRelease") {
            id = "android-library-release"
            implementationClass = "AndroidLibraryReleasePlugin"
        }
    }
}