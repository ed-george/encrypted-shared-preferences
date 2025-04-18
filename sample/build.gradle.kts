/*
 * Copyright 2025 (C) Ed Holloway-George • spght.dev
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import dev.spght.encryptedprefs.Configuration

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "dev.spght.encryptedprefs.sample"
    compileSdk = Configuration.compileSdk

    defaultConfig {
        applicationId = "dev.spght.encryptedprefs.sample"
        targetSdk = Configuration.targetSdk
        minSdk = Configuration.minSdk

        versionCode = Configuration.versionCode
        versionName = Configuration.versionName
        multiDexEnabled = true
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        viewBinding = true
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
}

dependencies {

    // Import sharedprefs-ktx for Kotlin projects
    implementation(projects.sharedprefsKtx)

    implementation(libs.androidx.appcompat)
    implementation(libs.material)
}