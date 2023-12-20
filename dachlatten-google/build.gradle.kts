plugins {
    id("android-library-base")
    id("android-library-unit-test")
    id("android-library-release")
}

dependencies {
    compileOnly(libs.coroutines.playServices)

    testImplementation(libs.coroutines.playServices)
}
