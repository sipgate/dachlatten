plugins {
    id("android-library-base")
    id("kotlin-library-unit-test")
    id("android-library-release")
}

dependencies {
    compileOnly(libs.kotlinx.datetime)

    testImplementation(libs.kotlinx.datetime)
}

android {
    publishing {
        singleVariant("release") {
            withSourcesJar()
        }
    }
}
