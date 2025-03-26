plugins {
    id("android-library-base")
    id("kotlin-library-unit-test")
    id("android-library-release")
    alias(libs.plugins.kotlin.serialization)
}

dependencies {
    implementation(libs.kotlinx.serialization)
    implementation(libs.retrofit.serialization)
    implementation(libs.okhttp)

    testImplementation(libs.kotlinx.serialization)
    testImplementation(libs.okhttp.mockwebserver)
}

android {
    publishing {
        singleVariant("release") {
            withSourcesJar()
        }
    }
}
