plugins {
    alias(libs.plugins.android.library)
    kotlin("android")
}

android {
    namespace = "com.saidatmaca.feature"
    compileSdk = 34

    defaultConfig {
        minSdk = 26

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release"){
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")

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
        kotlinCompilerExtensionVersion ="1.4.3"
    }
    packaging {
        resources {
            exclude("/META-INF/{AL2.0,LGPL2.1}")
        }
    }
    buildFeatures {
        buildConfig = true
        viewBinding= true
    }
}

dependencies {

    implementation(project(":feature:presentation"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.material)
    implementation(libs.androidx.material.icons.core.android)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.material3)


    //material
    implementation("androidx.compose.material3:material3:1.2.1")
    implementation("androidx.compose.material3:material3-window-size-class:1.2.1")
    implementation("androidx.compose.material:material-icons-extended:1.6.7")

    //navigation
    implementation("androidx.navigation:navigation-compose:2.5.3")

    implementation ("com.airbnb.android:lottie-compose:6.0.0")

    implementation("io.coil-kt:coil-compose:2.6.0")
    implementation("io.coil-kt:coil-svg:2.2.2")

    //Glide compose
    implementation("com.github.bumptech.glide:compose:1.0.0-beta01")

    implementation("com.github.jaikeerthick:Composable-Graphs:v1.2.3") //ex: v1.2.3



}