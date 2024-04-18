plugins {
    id("android-library-base")
    id("kotlin-library-unit-test")
    id("android-library-release")
}

dependencies {
    compileOnly(libs.kotlinx.datetime)
    compileOnly(libs.android.support.annotations)

    testImplementation(libs.kotlinx.datetime)
}
