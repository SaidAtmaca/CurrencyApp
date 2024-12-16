plugins {
    id("currency.android.library")
    id("currency.android.hilt")
}

android {
    namespace = "com.saidatmaca.viewmodel"

}

dependencies {
    implementation(libs.androidx.lifecycle.viewModelCompose)

}