@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id("android-library-base")
    `maven-publish`
    signing
}

android {
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

dependencies {
    implementation(libs.coroutines)

    testImplementation(libs.junit)
    testImplementation(libs.coroutinesTest)
    testImplementation(libs.turbine)
}

publishing {
    publications {
        register<MavenPublication>("release") {
            groupId = "de.sipgate"
            artifactId = "dachlatten-flow"
            version = "0.1"

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

            afterEvaluate {
                from(components["release"])
            }
        }
    }
}

signing {
    @Suppress("LocalVariableName") val SIGNING_KEY: String? by project
    @Suppress("LocalVariableName") val SIGNING_PASSWORD: String? by project
    useInMemoryPgpKeys(SIGNING_KEY, SIGNING_PASSWORD)
    sign(publishing.publications["release"])
}
