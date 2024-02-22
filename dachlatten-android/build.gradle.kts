plugins {
    id("android-library-base")
    id("android-library-unit-test")
    id("android-library-robolectric-test")
    id("android-library-release")
}

dependencies {
    compileOnly(project(":dachlatten-text"))
    compileOnly(libs.androidx.activity)

    implementation(libs.androidx.annotation)
    implementation(libs.androidx.lifecycle.process)
    implementation(libs.kotlinx.coroutines)

    testImplementation(project(":dachlatten-text"))
    testImplementation(libs.androidx.activity)
    testImplementation(libs.androidx.core)
}
