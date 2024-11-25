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
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
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
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)




        val composeBom = platform("androidx.compose:compose-bom:2024.09.03")
        implementation(composeBom)
        androidTestImplementation(composeBom)


        implementation(libs.material3)
        // or Material De
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


        implementation(libs.androidx.activity.compose.v193)
        implementation(libs.androidx.lifecycle.viewmodel.compose)
        implementation(libs.androidx.runtime.livedata)
        implementation(libs.androidx.runtime.rxjava2)
        implementation(kotlin("script-runtime"))
        implementation(platform(libs.firebase.bom))
        implementation(libs.firebase.auth.ktx)
        implementation(libs.play.services.auth)


}