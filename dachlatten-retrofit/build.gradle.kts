plugins {
    id("android-library-base")
    id("android-library-unit-test")
    id("android-library-release")
    alias(libs.plugins.kotlin.serialization.plugin)
}

dependencies {
    implementation(libs.kotlinx.serialization)
    implementation(libs.retrofit.serialization)
    implementation(libs.okhttp)

    testImplementation(libs.kotlinx.serialization)
    testImplementation(libs.okhttp.mockwebserver)
}
