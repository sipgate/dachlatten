plugins {
    `kotlin-dsl`
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.kover.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("androidLibraryBase") {
            id = "android-library-base"
            implementationClass = "AndroidLibraryBasePlugin"
        }
        register("androidLibraryUnitTest") {
            id = "android-library-unit-test"
            implementationClass = "AndroidLibraryUnitTestPlugin"
        }
        register("androidLibraryRelease") {
            id = "android-library-release"
            implementationClass = "AndroidLibraryReleasePlugin"
        }
        register("androidLibraryRobolectricTest") {
            id = "android-library-robolectric-test"
            implementationClass = "AndroidLibraryRobolectricTestPlugin"
        }
    }
}
