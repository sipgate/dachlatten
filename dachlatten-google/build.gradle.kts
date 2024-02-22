plugins {
    id("android-library-base")
    id("android-library-unit-test")
    id("android-library-release")
}

dependencies {
    compileOnly(libs.kotlinx.coroutines.play.services)

    testImplementation(libs.kotlinx.coroutines.play.services)
}
