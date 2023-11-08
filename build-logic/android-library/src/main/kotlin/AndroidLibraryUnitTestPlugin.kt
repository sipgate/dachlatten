import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.tasks.testing.Test
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.withType

class AndroidLibraryUnitTestPlugin: Plugin<Project> {
    override fun apply(target: Project) {
        with (target) {
            setupTestDeps()
            setupTestRunner()
        }
    }
}

private fun Project.setupTestDeps() {
    val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

    dependencies {
        add("testImplementation", platform("org.junit:junit-bom:${libs.findVersion("junit5").get()}"))
        add("testImplementation", "org.junit.jupiter:junit-jupiter")
        add("testImplementation", libs.findLibrary("coroutinesTest").get())
        add("testImplementation", libs.findLibrary("turbine").get())
    }
}

private fun Project.setupTestRunner() {
    tasks.withType<Test>().configureEach {
        useJUnitPlatform()

        outputs.upToDateWhen { false }
    }
}
