import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

class AndroidLibraryRobolectricTestPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            setUpRobolectric()
        }
    }
}

private fun Project.setUpRobolectric() {
    val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

    dependencies {
        add("testImplementation", "org.robolectric:robolectric:${libs.findVersion("robolectric").get()}")
        add("testImplementation", libs.findLibrary("junit4").get())

        add("testImplementation", platform("org.junit:junit-bom:${libs.findVersion("junit5").get()}"))
        add("testRuntimeOnly", "org.junit.vintage:junit-vintage-engine")
        add("testImplementation", "org.junit.platform:junit-platform-suite")
        add("testRuntimeOnly", "org.junit.platform:junit-platform-launcher")
        add("testRuntimeOnly", "org.junit.platform:junit-platform-console")
    }

    extensions.configure<LibraryExtension> {
        testOptions {
            unitTests {
                isIncludeAndroidResources = true
            }
        }
    }
}
