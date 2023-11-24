plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id ("androidx.navigation.safeargs")
    id("kotlin-kapt")
    id("kotlin-parcelize")
}

android {
    namespace = "com.payment.aeps"
    compileSdk = 33

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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

    productFlavors {


    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        viewBinding = true
    }
}

dependencies {

//    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")


    val nav_version = "2.7.1"
    val lifecycle_version = "2.7.0-alpha01"
    // Navigation component
    implementation("androidx.navigation:navigation-fragment-ktx:$nav_version")
    implementation("androidx.navigation:navigation-ui-ktx:$nav_version")

    //videoModel mvvm
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle_version")
    implementation( "androidx.lifecycle:lifecycle-livedata-ktx:$lifecycle_version")
    implementation ("androidx.multidex:multidex:2.0.1")


    //    Epoxy
    val epoxy_version = "4.6.3"
    implementation("com.airbnb.android:epoxy:$epoxy_version")
    kapt("com.airbnb.android:epoxy-processor:$epoxy_version")

    val lottieVersion = "5.2.0"
    implementation("com.airbnb.android:lottie:$lottieVersion")
    implementation("com.github.FunkyMuse.KAHelpers:viewbinding:3.1.8")


    val moshi_version = "1.15.0"
    implementation("com.squareup.moshi:moshi:$moshi_version")
    kapt("com.squareup.moshi:moshi-kotlin-codegen:$moshi_version")




    val retrofit_version = "2.9.0"
    implementation("com.github.skydoves:sandwich:1.2.7")
    implementation("com.squareup.retrofit2:retrofit:$retrofit_version")
    implementation("com.squareup.retrofit2:converter-moshi:$retrofit_version")
    implementation("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.9")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    implementation("com.github.bumptech.glide:glide:4.15.1")
    implementation("com.karumi:dexter:6.2.2")

}