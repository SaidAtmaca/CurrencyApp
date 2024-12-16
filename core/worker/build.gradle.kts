plugins {
    id("currency.android.library")
    id("currency.android.hilt")
}

android {
    namespace = "com.saidatmaca.worker"

}

dependencies {


    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // Kotlin + coroutines
    implementation(libs.androidx.work.runtime.ktx)
    implementation(libs.androidx.hilt.work)
}