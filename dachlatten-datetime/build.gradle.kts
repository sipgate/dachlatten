plugins {
    id("kotlin-library")
    id("kotlin-library-unit-test")
}

dependencies {
    compileOnly(libs.kotlinx.datetime)

    testImplementation(libs.kotlinx.datetime)
}
