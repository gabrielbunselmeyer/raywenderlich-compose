buildscript {
    buildscript {
        dependencies {
            classpath(libs.gradle)
        }
    }
}

// The Kotlin DSL doesn't like that we're calling libs here. Temporarily suppressing.
// Issue: https://youtrack.jetbrains.com/issue/KTIJ-19369
@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.android.app) apply false
    alias(libs.plugins.android.lib) apply false
    alias(libs.plugins.kotlin) apply false
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
