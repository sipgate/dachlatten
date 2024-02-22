import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.get

class KotlinLibraryPlugin: Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("org.jetbrains.kotlin.jvm")
                apply("maven-publish")
                apply("signing")
            }

            setupVersionInfo()
            afterEvaluate {
                setupPublishing(components["java"])
            }
            setupSigning()
        }
    }
}
