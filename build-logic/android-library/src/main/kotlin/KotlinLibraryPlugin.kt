import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.get
import org.jetbrains.kotlin.gradle.dsl.ExplicitApiMode
import org.jetbrains.kotlin.gradle.dsl.kotlinExtension

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

        target.kotlinExtension.explicitApi = ExplicitApiMode.Strict
    }
}
