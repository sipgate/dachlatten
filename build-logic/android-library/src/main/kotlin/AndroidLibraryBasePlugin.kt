import com.android.build.gradle.LibraryExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinVersion
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

class AndroidLibraryBasePlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.library")
                apply("org.jetbrains.kotlin.android")
            }

            extensions.configure<LibraryExtension> {
                namespace = "de.sipgate.${target.name.replace("-", ".")}"
                compileSdk = 36

                defaultConfig.minSdk = 23
            }

            setJdkVersion(JavaVersion.VERSION_1_8)
            enableContextParameters()
        }
    }
}

private fun Project.setJdkVersion(version: JavaVersion) {
    extensions.configure<LibraryExtension> {
        compileOptions {
            sourceCompatibility = version
            targetCompatibility = version
        }
    }

    tasks.withType<KotlinCompile>().configureEach {
        compilerOptions.jvmTarget.set(JvmTarget.fromTarget(version.toString()))
    }
}

private fun Project.enableContextParameters() {
    tasks.withType<KotlinCompile>().configureEach {
        compilerOptions {
            languageVersion.set(KotlinVersion.KOTLIN_2_2)
            freeCompilerArgs.addAll(listOf(
                "-Xcontext-parameters"
                )
            )
        }
    }
}
