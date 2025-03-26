plugins {
    id("android-library-base")
    id("kotlin-library-unit-test")
    id("android-library-release")
}

dependencies {
    api(project(":dachlatten-datetime"))

    compileOnly(libs.kotlinx.datetime)
    compileOnly(libs.androidx.core)
    compileOnly(libs.androidx.compose.animation)
    compileOnly(libs.android.support.annotations)

    testImplementation(libs.kotlinx.datetime)
}

android {
    publishing {
        singleVariant("release") {
            withSourcesJar()
        }
    }
}
