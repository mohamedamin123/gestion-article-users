plugins {
    id("com.android.application") version "8.5.2" // Adjust the version as needed
    kotlin("android") version "1.8.0" // This is optional if you need Kotlin support elsewhere
}

android {
    namespace = "com.example.gestion_de_stock"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.gestion_de_stock"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        viewBinding = true
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
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    // Room database
    implementation(libs.room.runtime)
    annotationProcessor(libs.room.compiler) // Use annotationProcessor for Java
    androidTestImplementation(libs.room.testing)

    // CircleImageView dependency
    implementation(libs.circleimageview)

    // Lombok
    implementation(libs.lombok)
    annotationProcessor(libs.lombok)

    // Gson dependency
    implementation(libs.gson)
    //glide image
    implementation (libs.glide)
    //test
    testImplementation ("junit:junit:4.13.2")
    androidTestImplementation ("androidx.test.ext:junit:1.1.5")
    androidTestImplementation ("androidx.test:core:1.5.0")
    androidTestImplementation ("androidx.test:runner:1.5.0")
    androidTestImplementation ("androidx.arch.core:core-testing:2.2.0")
    //notification
    implementation ("androidx.core:core-ktx:1.8.0")


}
