import com.android.build.api.variant.LibraryAndroidComponentsExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.ExplicitApiMode
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinVersion
import org.jetbrains.kotlin.gradle.dsl.kotlinExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

class AndroidLibraryBasePlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.library")
            }

            extensions.configure<LibraryAndroidComponentsExtension> {
                finalizeDsl { extension ->
                    extension.namespace = "de.sipgate.${target.name.replace("-", ".")}"
                    extension.compileSdk = 36
                    extension.defaultConfig.minSdk = 23

                    extension.compileOptions {
                        sourceCompatibility = JAVA_VERSION
                        targetCompatibility = JAVA_VERSION
                    }
                }
            }

            setJdkVersion(JAVA_VERSION)
            setKotlinVersion(KOTLIN_VERSION)
        }

        target.kotlinExtension.explicitApi = ExplicitApiMode.Strict
    }
}

private fun Project.setJdkVersion(version: JavaVersion) {
    extensions.configure<LibraryAndroidComponentsExtension> {
        finalizeDsl { extension ->
            extension.compileOptions {
                sourceCompatibility = version
                targetCompatibility = version
            }
        }
    }

    tasks.withType<KotlinCompile>().configureEach {
        compilerOptions.jvmTarget.set(JvmTarget.fromTarget(version.toString()))
    }
}

private fun Project.setKotlinVersion(version: KotlinVersion) {
    tasks.withType<KotlinCompile>().configureEach {
        compilerOptions {
            languageVersion.set(version)
        }
    }
}
