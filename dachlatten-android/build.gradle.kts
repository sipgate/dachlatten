plugins {
    id("android-library-base")
    id("android-library-unit-test")
    id("android-library-robolectric-test")
    id("android-library-release")
}

dependencies {
    compileOnly(libs.androidx.activity)

    implementation(libs.androidx.annotation)
    implementation(libs.androidx.lifecycle.process)
    implementation(libs.kotlinx.coroutines)

    testImplementation(libs.androidx.activity)
    testImplementation(libs.androidx.core)
}
