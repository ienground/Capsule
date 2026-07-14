import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.compose.multiplatform)
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "com.kyant.capsule.demo"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.kyant.capsule.demo"
        minSdk = 23
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0.0"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
}

kotlin {
    target {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_21)
        }
    }

    dependencies {
        implementation(projects.example.composeApp)
        implementation(libs.androidx.activity.compose)
        implementation(libs.androidx.ui)
        implementation(libs.androidx.foundation)
        implementation(libs.androidx.runtime)
    }
}
