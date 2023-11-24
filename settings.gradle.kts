pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        jcenter()
        maven { url = uri("https://jitpack.io" ) }
        maven("https://kotlin.bintray.com/kotlinx")
    }
}

rootProject.name = "Ayush Digitils"
include(":app")
include(":aeps")
