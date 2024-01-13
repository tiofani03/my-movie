plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
}

apply(from = "../shared_dependencies.gradle")

android {
    namespace = "com.tiooooo.mymovie"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.tiooooo.mymovie"
        minSdk = 21
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    viewBinding {
        enable = true
    }
}

dependencies {
    implementation(project(":data:movie"))
    implementation(project(":core"))

    //dagger
    implementation("com.google.dagger:hilt-android:2.48")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    kapt("com.google.dagger:hilt-android-compiler:2.48")

    //viewmodel
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.7.0")
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.6")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.6")

    //bottom bar
    implementation("com.github.ertugrulkaragoz:SuperBottomBar:0.4")

    //concat adapter
    implementation("androidx.recyclerview:recyclerview:1.3.2")

    //coil
    implementation("io.coil-kt:coil:2.5.0")
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")
    implementation("com.flaviofaria:kenburnsview:1.0.7")
    implementation("com.facebook.shimmer:shimmer:0.5.0")
    implementation("com.pierfrancescosoffritti.androidyoutubeplayer:core:12.1.0")
}

kapt {
    correctErrorTypes = true
}


