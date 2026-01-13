plugins {
    alias(libs.plugins.android.application)
    id("com.google.dagger.hilt.android") version "2.57.2"
}



android {
    namespace = "com.learn.paginglibrary"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "com.learn.paginglibrary"
        minSdk = 24
        targetSdk = 36
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
    buildFeatures{
        viewBinding=true

    }
    hilt {
        enableAggregatingTask = false
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

    //retrofit
    val retrofit_version = "3.0.0";
    implementation("com.squareup.retrofit2:retrofit:${retrofit_version}")
    implementation("com.google.code.gson:gson:2.13.2")
    implementation("com.squareup.retrofit2:converter-gson:${retrofit_version}")

    // RxJava3 with retrofit
    implementation("com.squareup.retrofit2:adapter-rxjava3:${retrofit_version}")
    //paging library
    val paging_version = "3.3.6"
    implementation("androidx.paging:paging-runtime:${paging_version}")
    // optional - RxJava3 support
    implementation("androidx.paging:paging-rxjava3:${paging_version}")

    //hilt dagger
    implementation ("com.google.dagger:hilt-android:2.57.2")
    annotationProcessor ("com.google.dagger:hilt-compiler:2.57.2")

    //Glide
    implementation ("com.github.bumptech.glide:glide:4.14.2")
    annotationProcessor ("com.github.bumptech.glide:compiler:4.14.2")

    val lifecycle_version = "2.10.0"
    // ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel:${lifecycle_version}")
    // LiveData
    implementation("androidx.lifecycle:lifecycle-livedata:${lifecycle_version}")

}