import com.lagradost.cloudstream3.gradle.CloudstreamExtension

buildscript {apply(plugin = "com.lagradost.cloudstream3.gradle")
    repositories {
        google()
        mavenCentral()
        maven("https://jitpack.io")
    }
    dependencies {
        classpath("com.android.tools.build:gradle:8.2.2")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.9.22")
        // Version yahan fix kar diya gaya hai (master-SNAPSHOT)
        classpath("com.github.recloudstream:gradle:master-SNAPSHOT")
    }
}

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    
}

android {
    compileSdk = 34

    defaultConfig {
        minSdk = 21
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }
}

cloudstream {
    // Agar Repo plugin error de, toh is line ko uncomment karein:
    // setRepo(com.lagradost.cloudstream3.gradle.Repo.Plugin)
}
