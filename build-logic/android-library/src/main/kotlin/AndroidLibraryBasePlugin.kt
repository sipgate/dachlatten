import com.android.build.gradle.LibraryExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

class AndroidLibraryBasePlugin: Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.library")
                apply("org.jetbrains.kotlin.android")
            }

            extensions.configure<LibraryExtension> {
                namespace = "de.sipgate.${target.name.replace("-", ".")}"
                compileSdk = 35

                defaultConfig.minSdk = 23
            }

            setJdkVersion(JavaVersion.VERSION_1_8)
            enableContextReceivers()
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

private fun Project.enableContextReceivers() {
    tasks.withType<KotlinCompile>().configureEach {
        kotlinOptions {
            languageVersion = "2.0"
            freeCompilerArgs = freeCompilerArgs + listOf(
                "-Xcontext-receivers"
            )
        }
    }
}
