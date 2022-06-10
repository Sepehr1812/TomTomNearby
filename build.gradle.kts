// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    dependencies {
        classpath(AppDependencies.daggerHiltGradlePlugin)
        classpath(AppDependencies.secretsGradlePlugin)
        classpath(AppDependencies.androidxNavigationSafeArgsGradlePlugin)
    }
}

plugins {
    id(AppDependencies.androidApplication) version Versions.androidApplication apply false
    id(AppDependencies.androidLibrary) version Versions.androidLibrary apply false
    id(AppDependencies.kotlinAndroid) version Versions.kotlinAndroid apply false
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}