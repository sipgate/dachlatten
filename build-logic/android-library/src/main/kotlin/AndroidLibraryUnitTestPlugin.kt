import kotlinx.kover.gradle.plugin.dsl.KoverProjectExtension
import kotlinx.kover.gradle.plugin.dsl.KoverReportExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.tasks.testing.Test
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.withType

class AndroidLibraryUnitTestPlugin: Plugin<Project> {
    override fun apply(target: Project) {
        with (target) {
            setupKover()
            setupTestDeps()
            setupTestRunner()
        }
    }
}

private fun Project.setupKover() {
    pluginManager.apply("org.jetbrains.kotlinx.kover")

    extensions.configure<KoverReportExtension> {
        this.defaults{
            this.mergeWith("release")
        }
    }

    dependencies {
        add("kover", project(":${this@setupKover.name}"))
    }
}

private fun Project.setupTestDeps() {
    val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

    dependencies {
        add("testImplementation", platform("org.junit:junit-bom:${libs.findVersion("junit5").get()}"))
        add("testImplementation", "org.junit.jupiter:junit-jupiter")
        add("testImplementation", libs.findLibrary("kotlinx-coroutines-test").get())
        add("testImplementation", libs.findLibrary("turbine").get())
    }
}

private fun Project.setupTestRunner() {
    tasks.withType<Test>().configureEach {
        useJUnitPlatform()

        outputs.upToDateWhen { false }
    }
}
