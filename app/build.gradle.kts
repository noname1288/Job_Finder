plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.example.jobfinder"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.jobfinder"
        minSdk = 26
        targetSdk = 35
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.15"
    }

    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }


}

dependencies {
    implementation("androidx.compose.material:material:1.8.1")
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // https://mvnrepository.com/artifact/androidx.compose.material/material-icons-extended
    implementation("androidx.compose.material:material-icons-extended:1.7.5")
    // To use constraintlayout in compose
    implementation("androidx.constraintlayout:constraintlayout-compose:1.1.1")
    // https://mvnrepository.com/artifact/io.coil-kt/coil-compose
    implementation("io.coil-kt:coil-compose:2.7.0")
    // https://mvnrepository.com/artifact/io.coil-kt/coil-svg
    implementation("io.coil-kt:coil-svg:2.4.0")

    val nav_version = "2.8.9"
    implementation("androidx.navigation:navigation-compose:$nav_version")
//    // https://mvnrepository.com/artifact/androidx.lifecycle/lifecycle-livedata
//    implementation("androidx.lifecycle:lifecycle-livedata:2.8.7")
    //compose runtime - live data
    implementation("androidx.compose.runtime:runtime-livedata:1.7.8")

    // https://mvnrepository.com/artifact/com.google.code.gson/gson
    implementation("com.google.code.gson:gson:2.10.1")
    // https://mvnrepository.com/artifact/com.squareup.retrofit2/retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
// https://mvnrepository.com/artifact/com.squareup.retrofit2/converter-gson
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    //dataStore
    implementation("androidx.datastore:datastore-preferences:1.1.6")




}