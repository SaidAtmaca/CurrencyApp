plugins {
    id("currency.android.library")
    id("currency.android.library.compose")
}

android {
    namespace = "com.saidatmaca.feature"

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
    implementation(libs.lottie.compose)
    //Glide compose
    implementation(libs.compose)

    implementation(libs.composable.graphs) //ex: v1.2.3
    implementation(libs.coil.compose)
    implementation(libs.coil.svg)
    //material
    implementation(libs.material3)
    implementation(libs.androidx.material.icons.extended)
    implementation("androidx.compose.material3:material3-window-size-class:1.3.1")

    //navigation
    implementation(libs.androidx.navigation.compose)









}