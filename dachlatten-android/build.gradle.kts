plugins {
    id("android-library-base")
    id("android-library-unit-test")
    id("android-library-robolectric-test")
    id("android-library-release")
}

dependencies {
    compileOnly(libs.androidx.activity)

    implementation(libs.coroutines)
    implementation(libs.lifecycle.process)
    implementation(libs.annotation.jvm)

    testImplementation(libs.androidx.activity)
}
