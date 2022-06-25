object AppConfig {

    const val compileSdk = 32
    const val applicationId = "ir.sotoon.interviewtask"
    const val minSdk = 26
    const val targetSdk = 32
    const val versionCode = 1
    const val versionName = "1.0.0"

    const val androidTestInstrumentation = "androidx.test.runner.AndroidJUnitRunner"
    const val proguardRules = "proguard-rules.pro"
    const val consumerRules = "consumer-rules.pro"
    const val proguardOptimizeRules = "proguard-android-optimize.txt"

    /* build types */
    const val buildRelease = "release"
    const val buildDebug = "debug"

    /* module names */
    const val data = ":data"
    const val domain = ":domain"
}