plugins {
    id("android-library-base")
    id("kotlin-library-unit-test")
    id("android-library-robolectric-test")
    id("android-library-release")
}

dependencies {
    api(libs.androidx.lifecycle.process)

    implementation(project(":dachlatten-primitives"))
    implementation(libs.jetbrains.markdown)

    compileOnly(libs.androidx.compose.foundation)
    compileOnly(libs.androidx.compose.ui)

    testImplementation(libs.bundles.androidx.compose.ui.test)
    testImplementation(libs.androidx.compose.foundation)
}

android {
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
    }

    buildFeatures {
        compose = true
    }
}

