plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.kotlin.multiplatform.library)
    alias(libs.plugins.compose.multiplatform)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.vanniktech.mavenPublish)
}

kotlin {
    androidLibrary {
        namespace = "com.kyant.capsule"
        compileSdk = libs.versions.android.compileSdk.get().toInt()
        minSdk = libs.versions.android.minSdk.get().toInt()
    }

    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        commonMain.dependencies {
            implementation(compose.foundation)
            implementation(compose.runtime)
            implementation(compose.ui)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}