plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("org.jetbrains.kotlin.kapt")
}

android {
    namespace = "com.meher.semestersnap"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.meher.semestersnap"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.14"
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.ui)
    implementation(libs.material3)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.runtime.livedata)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    implementation(libs.koin.androidx.compose)
    implementation(libs.koin.android)
    implementation(libs.androidx.material.icons.core)
    implementation(libs.androidx.material.icons.extended)
    implementation(libs.ui.tooling.preview)
    debugImplementation(libs.ui.tooling)
    //noinspection KaptUsageInsteadOfKsp
    kapt(libs.androidx.room.compiler)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
}