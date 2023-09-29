@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id("android-library-base")
    id("android-library-release")
}

dependencies {
    implementation(libs.coroutines)

    testImplementation(libs.junit)
    testImplementation(libs.coroutinesTest)
    testImplementation(libs.turbine)
}
