pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
        maven(url = "https://jitpack.io")
        maven(url = "https://maven.google.com")
    }
}


dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven(url = "https://jitpack.io")
        maven(url = "https://maven.google.com")
    }
}

rootProject.name = "Pulse"
include(":app")

include(":core")
include(":core:data")
include(":core:domain")
include(":core:ui")

include(":features")

include(":features:media")
include(":features:media:data")
include(":features:media:domain")
include(":features:media:ui")
