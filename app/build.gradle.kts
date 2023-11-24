import com.payment.ayushdigitils.Dependencies as deps
plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id ("androidx.navigation.safeargs")
    id("kotlin-parcelize")
    id("kotlin-kapt")
}

android {
    namespace = "com.payment.ayushdigitils"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.payment.ayushdigitils"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        multiDexEnabled  = true
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField("String", "BASE_URL", "\"https://login.ayusdigital.co.in/\"")
        }
        debug {

            buildConfigField("String", "BASE_URL", "\"https://login.ayusdigital.co.in/\"")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures{
        buildConfig = true

        viewBinding = true

    }

    kapt {
        correctErrorTypes = true
    }
}

dependencies {

    implementation(deps.AndroidX.KotlinX.core_ktx)
    implementation(deps.AndroidX.Default.appcompat)
    implementation(deps.AndroidX.Default.material)
    implementation(deps.AndroidX.Default.constraintlayout)
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation(deps.AndroidX.Test.junit)
    androidTestImplementation(deps.AndroidX.Test.android_junit)
    androidTestImplementation(deps.AndroidX.Test.espresso)

    implementation(project(":aeps"))

    implementation(files("libs/onboarding.aar"))
//    implementation(files("libs/PS_MATM_2.0.2.aar"))


    //region navigation
    implementation(deps.AndroidX.Navigation.navigationUi)
    implementation(deps.AndroidX.Navigation.navigationFragment)
    //endregion navigation


    //region lifecycle
    implementation(deps.AndroidX.Lifecycle.viewModel)
    implementation(deps.AndroidX.Lifecycle.liveData)
    implementation(deps.AndroidX.Lifecycle.savedState)
    implementation(deps.AndroidX.Lifecycle.common)
    implementation(deps.AndroidX.Lifecycle.service)
    //endregion lifecycle

    //region epoxy
    implementation(deps.Epoxy.epoxy)
    implementation(deps.Epoxy.epoxyPaging)
    kapt(deps.Epoxy.epoxyProcessor)
    // endregion epoxy

    //region moshi
    implementation(deps.Moshi.moshi)
    kapt(deps.Moshi.moshiCodeGen)
    //endregion

    // region retrofit
    implementation(deps.Retrofit.retrofit)
    implementation(deps.Retrofit.gsonConverter)
    implementation(deps.Retrofit.moshiConverter)
    // endregion

    // region okhttp
    implementation(deps.OkHttp.okHttp)
    implementation(deps.OkHttp.loggingInterceptor)
    //endregion

    //region glide
    implementation(deps.Glide.glide)
    implementation(deps.Glide.okhttpIntegration)
    kapt(deps.Glide.compiler)
    implementation(deps.Glide.glideTransformation)
    //endregion

    //region koin
    implementation(deps.Koin.fragment)
    implementation(deps.Koin.scope)
    implementation(deps.Koin.viewModel)
    kapt(deps.Koin.gradlePlugin)
    //endregion



    implementation(deps.materialDialog)
    implementation(deps.lottie)
    implementation(deps.timber)
//    implementation(deps.flower)
    implementation(deps.connectionBuddy)
    implementation(deps.dexter)



    debugImplementation(deps.Debug.remoteDebugger)
    releaseImplementation(deps.Debug.remoteDebugger)

//region [onBoarding]
    implementation(deps.OnBoarding.rxjava2)
    implementation(deps.OnBoarding.rxandroid)
    implementation(deps.OnBoarding.gson)
    implementation(deps.OnBoarding.legacy)
    implementation(deps.OnBoarding.dexter)
    implementation(deps.OnBoarding.ucrop)
    implementation(deps.OnBoarding.multidex)
// endregion

    implementation(deps.avatargenerator)




}