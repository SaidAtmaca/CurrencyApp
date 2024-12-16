plugins {
    id("currency.android.application")
    id("currency.android.application.compose")
    id("currency.android.hilt")

}

android {
    namespace = "com.saidatmaca.currencyapp"

    defaultConfig {
        applicationId ="com.saidatmaca.currencyapp"

    }

    buildTypes {
        getByName("release"){
            isMinifyEnabled =false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")

        }


    }
    buildFeatures {
        compose= true
        buildConfig = true
        viewBinding =true
    }
    composeOptions {
        kotlinCompilerExtensionVersion ="1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

}

dependencies {

    implementation(project(":core:domain"))
    implementation(project(":core:network"))
    implementation(project(":core:data"))
    implementation(project(":core:common"))
    implementation(project(":core:worker"))
    implementation(project(":feature:presentation"))

    implementation(libs.androidx.core.ktx)
    implementation (libs.androidx.lifecycle.runtime.ktx)
    implementation (libs.androidx.activity.compose)
    implementation (platform(libs.androidx.compose.bom))
    implementation (libs.androidx.ui)
    implementation (libs.androidx.ui.graphics)
    implementation (libs.androidx.ui.tooling.preview)
    implementation (libs.androidx.material3)
    testImplementation (libs.junit)
    androidTestImplementation (libs.androidx.junit)
    androidTestImplementation (libs.androidx.espresso.core)
    androidTestImplementation (platform(libs.androidx.compose.bom))
    androidTestImplementation (libs.androidx.ui.test.junit4)
    debugImplementation (libs.androidx.ui.tooling)
    debugImplementation (libs.androidx.ui.test.manifest)
    // Room
    implementation(libs.androidx.room.runtime)
    kapt(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)
    //Retrofit
    implementation (libs.retrofit)
    implementation (libs.converter.gson)
    implementation (libs.okhttp.v500alpha2)
    implementation (libs.logging.interceptor.v500alpha2)

    // Kotlin + coroutines
    implementation(libs.androidx.work.runtime.ktx)
    implementation(libs.androidx.hilt.work)


}