plugins {
    id("currency.android.library")
    id("currency.android.room")
}

android {
    namespace = "com.saidatmaca.model"

}

dependencies {

    implementation(project(":core:common"))
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}