plugins {
    id("currency.android.library")
    id("currency.android.hilt")
}

android {
    namespace = "com.saidatmaca.network"

}

dependencies {
    implementation(project(":core:model"))
    implementation(project(":core:common"))
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation (libs.retrofit)
    implementation (libs.converter.gson)
    implementation (libs.okhttp.v500alpha2)
    implementation (libs.logging.interceptor.v500alpha2)

    implementation(libs.sandwich)
    implementation(libs.sandwich.retrofit)
}