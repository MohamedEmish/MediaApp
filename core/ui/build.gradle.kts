plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.hilt)
    id("kotlin-parcelize")
}

android {
    namespace = "com.amosh.pulse.core.ui"
    compileSdk = 36

    defaultConfig {
        minSdk = 26

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

    packaging {
        resources {
            excludes += setOf(
                "META-INF/LICENSE.md",
                "META-INF/LICENSE-notice.md"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
        freeCompilerArgs = freeCompilerArgs + listOf(
            "-Xopt-in=kotlin.RequiresOptIn",
            "-Xjvm-default=all",
        )
    }
}

dependencies {
    // Kotlin BOM
    api(platform(libs.kotlin.bom))

    // AndroidX Core
    api(libs.androidx.core)

    // Compose
    api(platform(libs.compose.bom))

    api(libs.compose.ui)
    api(libs.compose.material3)
    api(libs.compose.preview)
    api(libs.compose.tooling)
    api(libs.compose.animation)
    api(libs.compose.icons.core)
    api(libs.compose.icons.extended)
    api(libs.compose.constraint.layout)
    api(libs.compose.activity)

    // Lifecycle
    api(libs.lifecycle.runtime)
    api(libs.lifecycle.viewmodel)

    // Coil
    api(libs.coil)

    // Project Modules
    implementation(project(":core:data"))
    implementation(project(":core:domain"))

    // Lottie
    api(libs.lottie.compose)

    //Testing
    api(libs.bundles.testing)

    // Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)
    implementation(libs.hilt.navigation.compose)
}