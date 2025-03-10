plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "com.example.myapplication"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.myapplication"
        minSdk = 29
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildFeatures {
        compose = true
        viewBinding = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
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

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildFeatures{
        viewBinding = true
    }
}

dependencies {
    implementation(libs.dotsindicator)
    implementation (libs.coil)

    // Jika menggunakan Jetpack Compose
    implementation (libs.io.coil.kt.coil.compose)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.firebase.auth)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)

    implementation(libs.firebase.database.ktx)
    implementation(libs.googleid)
    implementation(libs.firebase.firestore.ktx)
    implementation(libs.play.services.mlkit.barcode.scanning)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Pastikan hanya satu versi Compose diimplementasikan
    implementation("androidx.compose.ui:ui:1.7.5")
    implementation("androidx.compose.material:material:1.7.5")
    implementation("androidx.compose.ui:ui-tooling-preview:1.7.5")
    implementation("androidx.activity:activity-compose:1.9.3")

    implementation ("androidx.navigation:navigation-fragment-ktx")
    implementation ("androidx.navigation:navigation-ui-ktx")
    implementation ("androidx.navigation:navigation-compose")

    implementation(libs.codescanner)
    implementation(libs.gpsCoroutines)
    implementation(libs.timber)


        val composeBom = platform("androidx.compose:compose-bom:2024.09.03")
        implementation(composeBom)
        androidTestImplementation(composeBom)


        implementation(libs.material3)
        // or Material Design 2
        implementation(libs.androidx.material)
        // or skip Material Design and build directly on top of foundational components
        implementation(libs.androidx.foundation)
        // or only import the main APIs for the underlying toolkit systems,
        // such as input and measurement/layout
        implementation(libs.ui)

        // Android Studio Preview support
        implementation(libs.ui.tooling.preview)
        debugImplementation(libs.ui.tooling)


        androidTestImplementation(libs.ui.test.junit4)
        debugImplementation(libs.ui.test.manifest)


        implementation(libs.androidx.material.icons.core)

        implementation(libs.androidx.material.icons.extended)

        implementation(libs.androidx.adaptive)
    implementation (libs.barcode.scanning)

        implementation(libs.androidx.activity.compose.v193)
        implementation(libs.androidx.lifecycle.viewmodel.compose)
        implementation(libs.androidx.runtime.livedata)
        implementation(libs.androidx.runtime.rxjava2)
        implementation(kotlin("script-runtime"))
        implementation(platform(libs.firebase.bom))
        implementation(libs.firebase.auth.ktx)
        implementation(libs.play.services.auth)

    implementation (libs.androidx.compose.material3.material3)
    implementation (libs.androidx.compose.ui.ui2)
    implementation (libs.androidx.compose.material.material2)



}
