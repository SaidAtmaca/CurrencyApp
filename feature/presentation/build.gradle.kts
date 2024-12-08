plugins {
    id("currency.android.library")
    id("currency.android.library.compose")
    id("currency.android.hilt")
}

android {
    namespace = "com.saidatmaca.presentation"

}

dependencies {

    implementation(project(":core:domain"))
    implementation(project(":core:model"))
    implementation(project(":core:common"))

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
    //navigation
    implementation(libs.androidx.navigation.compose)

    implementation (libs.lottie.compose)

    implementation(libs.coil.compose)
    implementation(libs.coil.svg)

    //Glide compose
    implementation(libs.compose)

    implementation(libs.composable.graphs) //ex: v1.2.3
    //material
    implementation(libs.material3)
    implementation(libs.androidx.material.icons.extended)
    implementation("androidx.compose.material3:material3-window-size-class:1.2.1")



}