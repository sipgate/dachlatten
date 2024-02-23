plugins {
    id("kotlin-library")
    id("kotlin-library-unit-test")
    alias(libs.plugins.kotlin.serialization)
}

dependencies {
    implementation(libs.androidx.annotation)

    compileOnly(libs.kotlinx.serialization)

    testImplementation(libs.kotlinx.serialization)
}
