subprojects.onEach {
    tasks.withType<Test>().configureEach {
        useJUnitPlatform()

        outputs.upToDateWhen { false }
    }
}
