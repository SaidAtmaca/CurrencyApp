// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.6.0" apply false
    id("com.android.library") version "8.6.0" apply false
    id("org.jetbrains.kotlin.android") version "2.0.20" apply false
    id("org.jetbrains.kotlin.plugin.compose") version "2.0.20"
    id("com.google.dagger.hilt.android") version "2.52" apply false

}

tasks.register("clean",Delete::class){
    delete(rootProject.buildDir)
}












/*
buildscript {
    dependencies {
    }
}
plugins {
    id 'com.android.application' version '8.1.4' apply false
    id 'org.jetbrains.kotlin.android' version '1.8.10' apply false
    id 'com.android.library' version '8.1.4' apply false
    id("com.google.dagger.hilt.android") version "2.44" apply false
}*/
