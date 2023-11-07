plugins {
    id("android-library-base")
    id("android-library-unit-test")
    id("android-library-robolectric-test")
    id("android-library-release")
}

dependencies {
    implementation(libs.coroutines)
    implementation(libs.lifecycle.process)
    implementation(libs.annotation.jvm)

    testImplementation(libs.coroutines)
}
