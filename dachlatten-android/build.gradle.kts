import com.android.build.api.dsl.LibraryExtension

extensions.configure<LibraryExtension> {
    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }
}