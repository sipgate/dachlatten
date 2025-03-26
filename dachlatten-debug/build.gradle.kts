plugins {
    id("android-library-base")
    id("kotlin-library-unit-test")
    id("android-library-release")
}

android {
    publishing {
        singleVariant("release") {
            withSourcesJar()
        }
    }
}
