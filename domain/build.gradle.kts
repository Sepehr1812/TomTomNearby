plugins {
    id(AppDependencies.androidLibrary)
    kotlin(AppDependencies.kotlinAndroidPlugin)
    kotlin(AppDependencies.kotlinKapt)
    id(AppDependencies.daggerHiltAndroidPlugin)
    id(AppDependencies.kotlinParcelize)
}

android {
    compileSdk = AppConfig.compileSdk

    defaultConfig {
        minSdk = AppConfig.minSdk
        targetSdk = AppConfig.targetSdk

        testInstrumentationRunner = AppConfig.androidTestInstrumentation
        consumerProguardFiles(AppConfig.consumerRules)
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
}

dependencies {

    api(AppDependencies.domainApiDeps)
    kapt(AppDependencies.domainKaptDeps)
}

kapt {
    correctErrorTypes = true
}