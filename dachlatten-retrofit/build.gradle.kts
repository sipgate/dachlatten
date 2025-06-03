plugins {
    id("kotlin-library")
    id("kotlin-library-unit-test")
    alias(libs.plugins.kotlin.serialization)
}

dependencies {
    implementation(libs.kotlinx.serialization)
    implementation(libs.retrofit.serialization)
    implementation(libs.okhttp)

    testImplementation(libs.kotlinx.serialization)
    testImplementation(libs.okhttp.mockwebserver)
}
