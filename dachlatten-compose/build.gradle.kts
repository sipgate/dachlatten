plugins {
    id("android-library-base")
    id("android-library-unit-test")
    id("android-library-robolectric-test")
    id("android-library-release")
}

dependencies {
    compileOnly(project(":dachlatten-text"))
    compileOnly(libs.androidx.compose.ui)

    testImplementation(project(":dachlatten-text"))
    testImplementation(libs.bundles.androidx.compose.ui.test)
}

android {
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
    }

    buildFeatures {
        compose = true
    }
}

