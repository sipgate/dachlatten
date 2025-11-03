plugins {
    id("kotlin-library")
    id("kotlin-library-unit-test")
}

dependencies {
    compileOnly(libs.kotlinx.coroutines)

    testImplementation(libs.kotlinx.coroutines)
    testImplementation(project(":dachlatten-primitives"))
}
