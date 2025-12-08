// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "9.0.0-beta03" apply false
    id("org.jetbrains.kotlin.plugin.compose") version "2.3.0-RC2" apply false
    id("org.jetbrains.kotlin.android") version "2.3.0-RC2" apply false
    id("org.jetbrains.kotlin.plugin.serialization") version "2.3.0-RC2" apply false
    id("com.google.devtools.ksp") version "2.3.3" apply false
}
true // Needed to make the Suppress annotation work for the plugins block