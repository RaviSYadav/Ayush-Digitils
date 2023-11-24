package com.payment.ayushdigitils

object Dependencies {

    object AndroidX {
        object KotlinX {
            const val core_ktx = "androidx.core:core-ktx:1.10.1"

        }

        object Default {
            const val appcompat = "androidx.appcompat:appcompat:1.6.1"
            const val material = "com.google.android.material:material:1.9.0"
            const val constraintlayout = "androidx.constraintlayout:constraintlayout:2.1.4"
        }

        object Test {
            const val junit = "junit:junit:4.13.2"
            const val android_junit = "androidx.test.ext:junit:1.1.5"
            const val espresso = "androidx.test.espresso:espresso-core:3.5.1"
        }


        object Navigation {
            private const val version = "2.7.1"
            const val navigationFragment = "androidx.navigation:navigation-fragment-ktx:$version"
            const val navigationUi = "androidx.navigation:navigation-ui-ktx:$version"
            const val safeargsGradlePlugin =
                "androidx.navigation:navigation-safe-args-gradle-plugin:$version"
        }

        object Lifecycle {
            private const val version = "2.7.0-alpha01"
            const val viewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:$version"
            const val liveData = "androidx.lifecycle:lifecycle-livedata-ktx:$version"
            const val savedState = "androidx.lifecycle:lifecycle-viewmodel-savedstate:$version"
            const val common = "androidx.lifecycle:lifecycle-common-java8:$version"
            const val service = "androidx.lifecycle:lifecycle-service:$version"
        }

    }

    object Epoxy {
        private const val version = "4.6.3"
        const val epoxy = "com.airbnb.android:epoxy:$version"
        const val epoxyPaging = "com.airbnb.android:epoxy-paging3:$version"
        const val epoxyProcessor = "com.airbnb.android:epoxy-processor:$version"
    }



    object Retrofit {
        private const val version = "2.9.0"
        const val retrofit = "com.squareup.retrofit2:retrofit:$version"
        const val gsonConverter = "com.squareup.retrofit2:converter-gson:$version"
        const val moshiConverter = "com.squareup.retrofit2:converter-moshi:$version"
    }

    object OkHttp {
        private const val version = "4.9.1"
        const val okHttp = "com.squareup.okhttp3:okhttp:$version"
        const val loggingInterceptor = "com.squareup.okhttp3:logging-interceptor:$version"
    }

    object Koin {
        private const val version = "2.2.3"
        const val fragment = "io.insert-koin:koin-androidx-fragment:$version"
        const val scope = "io.insert-koin:koin-androidx-scope:$version"
        const val viewModel = "io.insert-koin:koin-androidx-viewmodel:$version"
        const val androidxExt = "io.insert-koin:koin-androidx-ext:$version"
        const val gradlePlugin = "io.insert-koin:koin-gradle-plugin:$version"
    }

    object Moshi {
        private const val version = "1.15.0"
        const val moshi = "com.squareup.moshi:moshi-kotlin:$version"
        const val moshiCodeGen = "com.squareup.moshi:moshi-kotlin-codegen:1.15.0"
    }

    object Glide {
        private const val version = "4.13.0"
        const val glide = "com.github.bumptech.glide:glide:$version"
        const val compiler = "com.github.bumptech.glide:compiler:$version"
        const val okhttpIntegration = "com.github.bumptech.glide:okhttp3-integration:$version"
        const val glideTransformation = "jp.wasabeef:glide-transformations:4.3.0"
    }

    object Debug {
        const val remoteDebugger = "com.github.zerobranch.android-remote-debugger:debugger:1.1.0"
        const val remoteDebuggerNoop = "com.github.zerobranch.android-remote-debugger:noop:1.1.0"
    }

    object OnBoarding{

        const val rxjava2 = "com.squareup.retrofit2:adapter-rxjava2:2.3.0"
        const val rxandroid = "io.reactivex.rxjava2:rxandroid:2.0.1"
        const val gson = "com.google.code.gson:gson:2.5.2"
        const val legacy = "androidx.legacy:legacy-support-v4:1.0.0"
        const val dexter = "com.karumi:dexter:5.0.0"
        const val ucrop = "com.github.yalantis:ucrop:2.2.6-native"
        const val multidex = "androidx.multidex:multidex:2.0.1"
    }



    const val lottie = "com.airbnb.android:lottie:3.6.1"
    const val materialDialog = "com.shreyaspatil:MaterialDialog:2.1"
    const val timber = "com.jakewharton.timber:timber:5.0.1"
    const val flower = "com.github.hadiyarajesh:flower:1.0.0"
    const val connectionBuddy = "com.zplesac:connectionbuddy:2.0.0-beta1"
    const val dexter = "com.karumi:dexter:6.2.2"
    const val avatargenerator = "com.first.avatargenerator:AvatarImageGenerator:1.4"
}