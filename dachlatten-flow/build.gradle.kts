plugins {
    id("android-library-base")
    id("android-library-test")
    id("android-library-release")
}

dependencies {
    compileOnly(libs.coroutines)

    testImplementation(libs.coroutines)
}
