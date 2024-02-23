plugins {
    `kotlin-dsl`
}

dependencies {
    compileOnly(libs.android.gradle.plugin)
    compileOnly(libs.kotlin.gradle.plugin)
    compileOnly(libs.kover.gradle.plugin)
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
        register("androidLibraryRobolectricTest") {
            id = "android-library-robolectric-test"
            implementationClass = "AndroidLibraryRobolectricTestPlugin"
        }
        register("kotlinLibrary") {
            id = "kotlin-library"
            implementationClass = "KotlinLibraryPlugin"
        }
        register("kotlinLibraryUnitTest") {
            id = "kotlin-library-unit-test"
            implementationClass = "KotlinLibraryUnitTestPlugin"
        }
    }
}
