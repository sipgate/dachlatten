plugins {
    id("android-library-base")
    id("kotlin-library-unit-test")
    id("android-library-robolectric-test")
    id("android-library-release")
    alias(libs.plugins.compose.compiler)
}

dependencies {
    implementation(libs.jetbrains.markdown)

    compileOnly(libs.androidx.compose.foundation)
    compileOnly(libs.androidx.compose.ui)

    testImplementation(libs.bundles.androidx.compose.ui.test)
    testImplementation(libs.androidx.compose.foundation)
}

android {
    buildFeatures {
        compose = true
    }

    publishing {
        singleVariant("release") {
            withSourcesJar()
        }
    }
}
