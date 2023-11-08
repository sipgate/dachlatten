plugins {
    id("android-library-base")
    id("android-library-unit-test")
    id("android-library-robolectric-test")
    id("android-library-release")
}

dependencies {
    implementation(libs.compose.ui)
}

android {
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
    }

    buildFeatures {
        compose = true
    }
}

