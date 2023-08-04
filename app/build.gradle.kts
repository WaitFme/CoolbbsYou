@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.anpe.coolbbsyou"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.anpe.coolbbsyou"
        minSdk = 26
        targetSdk = 33
        versionCode = 1
        versionName = "0.23.0726"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    kotlin {
        jvmToolchain(8)
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(libs.core.ktx)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.activity.compose)
    implementation(platform(libs.compose.bom))
    implementation(libs.ui)
    implementation(libs.ui.graphics)
    implementation(libs.ui.tooling.preview)
    implementation(libs.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.ui.test.junit4)
    debugImplementation(libs.ui.tooling)
    debugImplementation(libs.ui.test.manifest)

    // User library
    implementation(libs.constraintlayout.compose)
    implementation(libs.navigation.compose)
    implementation(libs.accompanist.swiperefresh)
    implementation(libs.accompanist.systemuicontroller)
    implementation(libs.gson)
    implementation(libs.coil.compose)
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.material)
    implementation(libs.paging.runtime)
    implementation(libs.paging.compose)
    implementation(libs.jbcrypt)
    implementation(libs.jsoup)
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    ksp(libs.room.compiler)
}