plugins {
    id("android-library-base")
    id("android-library-test")
    id("android-library-release")
}

dependencies {
    implementation(libs.coroutines)
    implementation(libs.annotation.jvm)

    testImplementation(libs.coroutines)
}
