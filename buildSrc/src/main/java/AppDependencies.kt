object AppDependencies {

    const val androidApplication = "com.android.application"
    const val androidLibrary = "com.android.library"
    const val kotlinAndroid = "org.jetbrains.kotlin.android"
    const val kotlinKapt = "kotlin-kapt"
    const val daggerHiltAndroidPlugin = "dagger.hilt.android.plugin"
    const val androidxNavigationSafeargsKotlin = "androidx.navigation.safeargs.kotlin"
    const val secretsGradlePluginId =
        "com.google.android.libraries.mapsplatform.secrets-gradle-plugin"
    const val kotlinParcelize = "kotlin-parcelize"

    const val daggerHiltGradlePlugin =
        "com.google.dagger:hilt-android-gradle-plugin:${Versions.daggerHiltGradlePlugin}"
    const val secretsGradlePlugin =
        "com.google.android.libraries.mapsplatform.secrets-gradle-plugin:secrets-gradle-plugin:${Versions.secretsGradlePlugin}"
    const val androidxNavigationSafeArgsGradlePlugin =
        "androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.androidxNavigationSafeArgsGradlePlugin}"

    const val googlePlayLocation =
        "com.google.android.gms:play-services-location:${Versions.googlePlayLocation}"
    const val googlePlayMaps =
        "com.google.android.gms:play-services-maps:${Versions.googlePlayMaps}"
    const val androidMapsUtils =
        "com.google.maps.android:android-maps-utils:${Versions.androidMapsUtils}"

    const val androidxLifecycleLiveData =
        "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.androidxLifecycleLiveData}"
    const val androidxLifecycleViewmodel =
        "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.androidxLifecycleViewmodel}"

    const val androidxNavigationFragment =
        "androidx.navigation:navigation-fragment-ktx:${Versions.androidxNavigationFragment}"
    const val androidxNavigationUi =
        "androidx.navigation:navigation-ui-ktx:${Versions.androidxNavigationUi}"

    const val kotlinxCoroutinesCore =
        "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.kotlinxCoroutinesCore}"
    const val androidxCore = "androidx.core:core-ktx:${Versions.androidxCore}"
    const val androidxAppCompat = "androidx.appcompat:appcompat:${Versions.androidxAppCompat}"
    const val androidxConstraintLayout =
        "androidx.constraintlayout:constraintlayout:${Versions.androidxConstraintLayout}"
    const val androidMaterial = "com.google.android.material:material:${Versions.androidMaterial}"

    const val androidxRoom = "androidx.room:room-ktx:${Versions.androidxRoom}"
    const val androidxRoomCompiler = "androidx.room:room-compiler:${Versions.androidxRoomCompiler}"

    const val daggerHilt = "com.google.dagger:hilt-android:${Versions.daggerHilt}"
    const val daggerHiltCompiler = "com.google.dagger:hilt-compiler:${Versions.daggerHiltCompiler}"

    const val retrofitConverterGson =
        "com.squareup.retrofit2:converter-gson:${Versions.retrofitConverterGson}"
    const val retrofitGson = "com.google.code.gson:gson:${Versions.retrofitGson}"
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val okHttpLoggingInterceptor =
        "com.squareup.okhttp3:logging-interceptor:${Versions.okHttpLoggingInterceptor}"
}