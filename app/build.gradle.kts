plugins {
    id(AppDependencies.androidApplication)
    kotlin(AppDependencies.kotlinAndroidPlugin)
    kotlin(AppDependencies.kotlinKapt)
    id(AppDependencies.daggerHiltAndroidPlugin)
    id(AppDependencies.androidxNavigationSafeargsKotlin)
    id(AppDependencies.secretsGradlePluginId)
}

android {
    compileSdk = AppConfig.compileSdk

    defaultConfig {
        applicationId = AppConfig.applicationId
        minSdk = AppConfig.minSdk
        targetSdk = AppConfig.targetSdk
        versionCode = AppConfig.versionCode
        versionName = AppConfig.versionName

        testInstrumentationRunner = AppConfig.androidTestInstrumentation
    }

    buildTypes {
        getByName(AppConfig.buildRelease) {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile(AppConfig.proguardOptimizeRules),
                AppConfig.proguardRules
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }

    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(project(AppConfig.data))
    implementation(project(AppConfig.domain))

    implementation(AppDependencies.appImplDeps)
    kapt(AppDependencies.appKaptDeps)
}

kapt {
    correctErrorTypes = true
}