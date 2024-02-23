plugins {
    id("android-library-base")
    id("kotlin-library-unit-test")
    id("android-library-robolectric-test")
    id("android-library-release")
}

dependencies {
    implementation(project(":dachlatten-primitives"))

    compileOnly(libs.androidx.compose.ui)

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

