import com.android.build.api.variant.LibraryAndroidComponentsExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.component.SoftwareComponent
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.get
import org.gradle.kotlin.dsl.provideDelegate
import org.gradle.kotlin.dsl.register
import org.gradle.plugins.signing.SigningExtension

class AndroidLibraryReleasePlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("maven-publish")
                apply("signing")
            }

            setupReleaseBuild()
            setupVersionInfo()
            afterEvaluate {
                setupPublishing(components["release"])
            }
            setupSigning()
        }
    }
}

private fun Project.setupReleaseBuild() {
    extensions.configure<LibraryAndroidComponentsExtension> {
        finalizeDsl { extension ->
            extension.buildTypes {
                extension.buildTypes.getByName("release") {
                    isMinifyEnabled = false
                    proguardFiles(
                        extension.getDefaultProguardFile("proguard-android-optimize.txt"),
                        "proguard-rules.pro"
                    )
                }
            }
        }
    }
}

internal fun Project.setupVersionInfo() {
    project.version = getVersionName()
}

internal fun getVersionName() = "0.0.${System.getenv("GITHUB_RUN_NUMBER") ?: "0"}"

internal fun Project.setupPublishing(component: SoftwareComponent) {
    extensions.configure<PublishingExtension> {
        publications {
            register<MavenPublication>("release") {
                groupId = "de.sipgate"
                artifactId = project.name
                version = project.version.toString()

                setPom()

                from(component)
            }
        }

        repositories {
            maven {
                name = "GitHubPackages"
                url = uri("https://maven.pkg.github.com/sipgate/dachlatten")
                credentials {
                    username = "sipgate"
                    password = System.getenv("GITHUB_TOKEN")
                }
            }
            maven {
                name = "alphaDev"
                url = uri("https://maven.alphadev.net/releases")
                credentials {
                    username = "GitHub"
                    password = System.getenv("ADEV_TOKEN")
                }
            }
        }
    }
}

internal fun MavenPublication.setPom() {
    pom {
        name.set("Dachlatten-flow")
        description.set("")
        url.set("https://github.com/sipgate/dachlatten")
        licenses {
            license {
                name.set("The Apache License, Version 2.0")
                url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
            }
        }
        scm {
            connection.set("scm:git:git://git@github.com:sipgate/Dachlatten.git")
            developerConnection.set("scm:git:ssh://git@github.com:sipgate/dachlatten.git")
            url.set("https://github.com/sipgate/dachlatten")
        }
    }
}

fun Project.setupSigning() {
    extensions.configure<SigningExtension> {
        val signingKey: String? by project
        val signingPassword: String? by project
        useInMemoryPgpKeys(signingKey, signingPassword)
        sign(extensions.getByType(PublishingExtension::class.java).publications)
    }
}
