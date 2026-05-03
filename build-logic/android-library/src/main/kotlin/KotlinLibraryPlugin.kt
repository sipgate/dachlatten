import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.get
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.ExplicitApiMode
import org.jetbrains.kotlin.gradle.dsl.kotlinExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinVersion
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

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

            enableNewParamPropertyTargetBehavior()
        }

        target.kotlinExtension.explicitApi = ExplicitApiMode.Strict
    }
}

private fun Project.enableNewParamPropertyTargetBehavior() {
    tasks.withType<KotlinCompile>().configureEach {
        compilerOptions {
            languageVersion.set(KotlinVersion.KOTLIN_2_3)
            freeCompilerArgs.addAll(listOf(
                "-Xannotation-default-target=param-property"
                )
            )
        }
    }
}
