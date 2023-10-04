import com.android.build.gradle.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.publish.Publication
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
            val publication = setupPublishing()
            setupSigning(publication)
        }
    }
}

private fun Project.setupReleaseBuild() {
    extensions.configure<LibraryExtension> {
        buildTypes {
            release {
                isMinifyEnabled = false
                proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
                )
            }
        }
    }
}

private fun Project.setupPublishing(): Publication {
    extensions.configure<PublishingExtension> {
        publications {
            register<MavenPublication>("release") {
                groupId = "de.sipgate"
                artifactId = project.name
                version = "0.1"

                setPom()

                afterEvaluate {
                    from(components["release"])
                }
            }
        }
    }
    return extensions.getByType(PublishingExtension::class.java).publications["release"]
}

private fun MavenPublication.setPom() {
    pom {
        name.set("Dachlatten-flow")
        description.set("")
        url.set("https://github.com/sipgate/Dachlatten")
        licenses {
            license {
                name.set("The Apache License, Version 2.0")
                url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
            }
        }
        scm {
            connection.set("scm:git:git://git@github.com:sipgate/Dachlatten.git")
            developerConnection.set("scm:git:ssh://git@github.com:sipgate/Dachlatten.git")
            url.set("https://github.com/sipgate/Dachlatten")
        }
    }
}

private fun Project.setupSigning(publication: Publication) {
    extensions.configure<SigningExtension> {
        @Suppress("LocalVariableName") val SIGNING_KEY: String? by project
        @Suppress("LocalVariableName") val SIGNING_PASSWORD: String? by project
        useInMemoryPgpKeys(SIGNING_KEY, SIGNING_PASSWORD)
        sign(publication)
    }
}