plugins {
    id("com.android.application") version "9.0.0"
    id("org.jetbrains.kotlin.plugin.compose") version "2.3.0"
    id("org.jetbrains.kotlin.android") version "2.3.0"
    id("org.jetbrains.kotlin.plugin.serialization") version "2.3.0"
    id("com.google.devtools.ksp") version "2.3.3"
}

android {
    namespace = "com.example.rickandmorty"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.rickandmorty"
        minSdk = 28
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "com.kaspersky.kaspresso.runner.KaspressoRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
    buildFeatures {
        compose = true
    }
    packaging {
        resources {
            excludes += setOf(
                "META-INF/LICENSE.md",
                "META-INF/LICENSE-notice.md"
            )
        }
    }
}

kotlin {
    jvmToolchain(21)
}

dependencies {

    // Core
    implementation("androidx.core:core-ktx:1.17.0")
    implementation("androidx.navigation3:navigation3-runtime:1.1.0-alpha03")
    implementation("androidx.navigation3:navigation3-ui:1.1.0-alpha03")

    // Network
    val retrofitVersion = "3.0.0"
    implementation("com.squareup.retrofit2:retrofit:$retrofitVersion")
    implementation("com.squareup.retrofit2:converter-kotlinx-serialization:$retrofitVersion")
    implementation("com.squareup.okhttp3:okhttp:5.3.2")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.10.0")

    // DB
    val roomVersion = "2.8.4"
    ksp("androidx.room:room-compiler:$roomVersion")
    implementation("androidx.room:room-ktx:$roomVersion")

    // DI
    implementation("io.insert-koin:koin-androidx-compose:4.1.1")

    // Coil 3
    val coilVersion = "3.3.0"
    implementation("io.coil-kt.coil3:coil-compose:$coilVersion")
    implementation("io.coil-kt.coil3:coil-network-okhttp:$coilVersion")

    // Lottie
    val lottieVersion = "6.7.1"
    implementation("com.airbnb.android:lottie-compose:$lottieVersion")

    // UI
    implementation(platform("androidx.compose:compose-bom:2026.01.01"))
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.10.0")
    implementation("androidx.activity:activity-compose:1.12.3")

    implementation("androidx.compose.material3:material3:1.4.0")
    implementation("androidx.compose.material:material-icons-extended:1.7.8")
    implementation("androidx.compose.ui:ui-tooling-preview:1.10.2")
    implementation("androidx.constraintlayout:constraintlayout-compose:1.1.1")

    implementation("io.github.kyant0:backdrop:1.0.5")
    implementation("dev.chrisbanes.haze:haze:1.7.1")
    implementation("dev.chrisbanes.haze:haze-materials:1.7.1")

    // Testing
    androidTestUtil("androidx.test:orchestrator:1.6.1")
    androidTestImplementation("com.kaspersky.android-components:kaspresso:1.6.0")
    androidTestImplementation("com.kaspersky.android-components:kaspresso-compose-support:1.6.0")
    androidTestImplementation("com.kaspersky.android-components:kaspresso-allure-support:1.6.0")

    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.11.0-alpha04")
}

configurations.configureEach {
    resolutionStrategy {
        force("androidx.appcompat:appcompat:1.7.0")
        force("androidx.drawerlayout:drawerlayout:1.2.0")
        force("com.google.android.material:material:1.13.0")
    }
}