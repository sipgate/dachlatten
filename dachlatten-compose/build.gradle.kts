plugins {
    id("android-library-base")
    id("android-library-unit-test")
    id("android-library-robolectric-test")
    id("android-library-release")
}

dependencies {
    compileOnly(project(":dachlatten-android"))
    compileOnly(libs.compose.ui)

    testImplementation(project(":dachlatten-android"))
    testImplementation(libs.bundles.compose.ui.test)
}

android {
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
    }

    buildFeatures {
        compose = true
    }
}

