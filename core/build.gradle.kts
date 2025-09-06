import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp)
    alias(libs.plugins.room)
    alias(libs.plugins.kotlin.serialization)
}

val localProps = gradleLocalProperties(rootDir, providers)
val apiKey: String = localProps.getProperty("apikey") ?: ""
val apiURL: String = localProps.getProperty("baseApi") ?: ""

val imageApiURL : String = localProps.getProperty("baseImageApi") ?: ""
val imageApiKey : String = localProps.getProperty("imageApiKey") ?: ""

android {
    namespace = "org.soumen.core"
    compileSdk = 36

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }
    buildTypes {
        release {
            isMinifyEnabled = false
            buildConfigField("String", "API_KEY", "\"$apiKey\"")
            buildConfigField("String", "API_URL", "\"$apiURL\"")
            buildConfigField("String", "IMAGE_API_URL", "\"$imageApiURL\"")
            buildConfigField("String", "IMAGE_API_KEY", "\"$imageApiKey\"")
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }

        debug {
            buildConfigField("String", "API_KEY", "\"$apiKey\"")
            buildConfigField("String", "API_URL", "\"$apiURL\"")
            buildConfigField("String", "IMAGE_API_URL", "\"$imageApiURL\"")
            buildConfigField("String", "IMAGE_API_KEY", "\"$imageApiKey\"")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)


    api(libs.room.runtime)
    ksp(libs.room.compiler)

    api(libs.ktor.client.android)
    api(libs.ktor.client.core)
    api(libs.ktor.client.serialization.jvm)
    api(libs.ktor.client.logging)
    api(libs.ktor.client.okhttp)

    // Coroutines
    api(libs.coroutines.core)
    api(libs.coroutines.android)
    api(libs.retrofit.coroutines.adapter)

    // DI
    api(libs.koin.core)
    api(libs.koin.android)
    api(libs.koin.androidx.compose)
    api(libs.koin.compose)
    api(libs.koin.compose.viewmodel)
    api(libs.bundles.ktor)

    api(libs.androidx.navigation.compose)



    // WorkManager
    api(libs.androidx.work.runtime.ktx)

    api(libs.coil.compose)
    api(libs.coil.network.okhttp)
}

room {
    schemaDirectory("$projectDir/schemas")
}