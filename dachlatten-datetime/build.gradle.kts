plugins {
    id("android-library-base")
    id("android-library-unit-test")
    id("android-library-release")
}

dependencies {
    compileOnly(libs.kotlinx.datetime)
    compileOnly(libs.support.annotations)
}
