object AppDependencies {

    // plugins
    const val androidApplication = "com.android.application"
    const val androidLibrary = "com.android.library"
    const val kotlinAndroid = "org.jetbrains.kotlin.android"
    const val kotlinAndroidPlugin = "android"
    const val kotlinKapt = "kapt"
    const val daggerHiltAndroidPlugin = "dagger.hilt.android.plugin"
    const val androidxNavigationSafeargsKotlin = "androidx.navigation.safeargs.kotlin"
    const val secretsGradlePluginId =
        "com.google.android.libraries.mapsplatform.secrets-gradle-plugin"
    const val kotlinParcelize = "kotlin-parcelize"

    // project level dependencies
    const val daggerHiltGradlePlugin =
        "com.google.dagger:hilt-android-gradle-plugin:${Versions.daggerHiltGradlePlugin}"
    const val secretsGradlePlugin =
        "com.google.android.libraries.mapsplatform.secrets-gradle-plugin:secrets-gradle-plugin:${Versions.secretsGradlePlugin}"
    const val androidxNavigationSafeArgsGradlePlugin =
        "androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.androidxNavigationSafeArgsGradlePlugin}"

    // module level dependencies
    private const val googlePlayLocation =
        "com.google.android.gms:play-services-location:${Versions.googlePlayLocation}"
    private const val googlePlayMaps =
        "com.google.android.gms:play-services-maps:${Versions.googlePlayMaps}"
    private const val androidMapsUtils =
        "com.google.maps.android:android-maps-utils:${Versions.androidMapsUtils}"

    private const val androidxLifecycleLiveData =
        "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.androidxLifecycleLiveData}"
    private const val androidxLifecycleViewmodel =
        "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.androidxLifecycleViewmodel}"

    private const val androidxNavigationFragment =
        "androidx.navigation:navigation-fragment-ktx:${Versions.androidxNavigationFragment}"
    private const val androidxNavigationUi =
        "androidx.navigation:navigation-ui-ktx:${Versions.androidxNavigationUi}"

    private const val kotlinxCoroutinesCore =
        "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.kotlinxCoroutinesCore}"
    private const val androidxCore = "androidx.core:core-ktx:${Versions.androidxCore}"
    private const val androidxAppCompat =
        "androidx.appcompat:appcompat:${Versions.androidxAppCompat}"
    private const val androidxConstraintLayout =
        "androidx.constraintlayout:constraintlayout:${Versions.androidxConstraintLayout}"
    private const val androidMaterial =
        "com.google.android.material:material:${Versions.androidMaterial}"

    private const val androidxRoom = "androidx.room:room-ktx:${Versions.androidxRoom}"
    private const val androidxRoomCompiler =
        "androidx.room:room-compiler:${Versions.androidxRoomCompiler}"

    private const val daggerHilt = "com.google.dagger:hilt-android:${Versions.daggerHilt}"
    private const val daggerHiltCompiler =
        "com.google.dagger:hilt-compiler:${Versions.daggerHiltCompiler}"

    private const val retrofitConverterGson =
        "com.squareup.retrofit2:converter-gson:${Versions.retrofitConverterGson}"
    private const val retrofitGson = "com.google.code.gson:gson:${Versions.retrofitGson}"
    private const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    private const val okHttpLoggingInterceptor =
        "com.squareup.okhttp3:logging-interceptor:${Versions.okHttpLoggingInterceptor}"


    /* ***** Module Dependencies ***** */
    val appImplDeps = listOf(
        // core
        androidxCore,
        androidxAppCompat,
        // ui
        androidMaterial,
        androidxConstraintLayout,
        // hilt
        daggerHilt,
        // navigation
        androidxNavigationFragment,
        androidxNavigationUi,
        // lifecycle
        androidxLifecycleLiveData,
        androidxLifecycleViewmodel,
        // maps
        googlePlayLocation,
        googlePlayMaps,
        androidMapsUtils
    )

    val appKaptDeps = listOf(
        // hilt
        daggerHiltCompiler
    )

    val dataImplDeps = listOf(
        // hilt
        daggerHilt,
        // retrofit
        retrofit,
        retrofitConverterGson
    )

    val dataKaptDeps = listOf(
        // room
        androidxRoomCompiler,
        // hilt
        daggerHiltCompiler
    )

    val dataApiDeps = listOf(
        // room
        androidxRoom,
        // coroutines
        kotlinxCoroutinesCore,
        // retrofit
        okHttpLoggingInterceptor
    )

    val domainApiDeps = listOf(
        // gson
        retrofitConverterGson,
        retrofitGson,
        // hilt
        daggerHilt
    )

    val domainKaptDeps = listOf(
        // hilt
        daggerHiltCompiler
    )
}