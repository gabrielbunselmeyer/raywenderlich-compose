pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}

enableFeaturePreview("VERSION_CATALOGS")
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }

    versionCatalogs {
        create("libs") {
            version("compose", "1.1.0-rc01")
            version("gradle", "7.0.0")
            version("android", "7.1.0-rc01")

            alias("gradle").to("com.android.tools.build", "gradle").versionRef("gradle")
            alias("android-app").toPluginId("com.android.application").versionRef("android")
            alias("android-lib").toPluginId("com.android.library").versionRef("android")
            alias("kotlin").toPluginId("org.jetbrains.kotlin.android").version("1.6.0")

            alias("androidx-core").to("androidx.core:core-ktx:1.6.0")
            alias("androidx-compose-ui").to("androidx.compose.ui", "ui").versionRef("compose")
            alias("androidx-compose-ui-tooling-preview").to("androidx.compose.ui", "ui-tooling-preview").versionRef("compose")
            alias("androidx-compose-ui-tooling").to("androidx.compose.ui", "ui-tooling").versionRef("compose")
            alias("androidx-compose-ui-test-junit").to("androidx.compose.ui", "ui-test-junit4").versionRef("compose")
            alias("androidx-compose-material").to("androidx.compose.material", "material").versionRef("compose")
            alias("androidx-lifecycle").to("androidx.lifecycle", "lifecycle-runtime-ktx").version("2.3.1")
            alias("androidx-activity").to("androidx.activity", "activity-compose").version("1.3.1")
            alias("androidx-navigation").to("androidx.navigation", "navigation-compose").version("2.4.0-rc01")

            bundle("androidx", listOf("androidx-core", "androidx-compose-ui",
                "androidx-compose-ui-tooling-preview", "androidx-compose-material",
                "androidx-lifecycle", "androidx-activity", "androidx-navigation"))
        }
    }
}

rootProject.name = "RayWenderlich Compose Viewer"
include(":app")
