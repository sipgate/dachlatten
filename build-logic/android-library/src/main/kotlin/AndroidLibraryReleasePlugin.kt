import com.android.build.gradle.LibraryExtension
import java.io.File
import java.util.Properties
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

internal fun Project.setupVersionInfo() {
    val versionProperties = File(project.rootDir, "version.properties")
    versionProperties.inputStream().use { inputStream ->
        Properties().apply {
            load(inputStream)
            project.version = getVersionName()
        }
    }
}

internal fun Properties.getVersionName(): String {
    val major = (get("majorVersion") as String).toInt()
    val minor = (get("minorVersion") as String).toInt()
    val patch = (get("patchVersion") as String).toInt()
    return "$major.$minor.$patch"
}

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
        }
    }
}

internal fun MavenPublication.setPom() {
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

fun Project.setupSigning() {
    extensions.configure<SigningExtension> {
        val signingKey: String? by project
        val signingPassword: String? by project
        useInMemoryPgpKeys(signingKey, signingPassword)
        sign(extensions.getByType(PublishingExtension::class.java).publications)
    }
}
