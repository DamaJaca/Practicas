buildscript {
    dependencies {
        classpath(libs.google.services)
        //Ktor
        classpath("org.jetbrains.kotlin:kotlin-serialization:1.5.21")
    }
}
// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.jetbrainsKotlinAndroid) apply false

    //Dagger Hilt
    id ("com.google.dagger.hilt.android") version "2.51.1" apply false

    //SafeArgs
    id ("androidx.navigation.safeargs.kotlin") version "2.7.7" apply false


}