plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    kotlin("kapt")
    id("com.google.devtools.ksp")
    alias(libs.plugins.dagger.hilt.plugin)
}

android {
    namespace = "com.ashkan.samplecompose"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.ashkan.samplecompose"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        //testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        testInstrumentationRunner = "com.ashkan.samplecompose.CustomTestRunner"
    }

    flavorDimensions += "env"

    productFlavors {
        create("develop") {
            dimension = "env"
            applicationIdSuffix = ".dev"
            versionNameSuffix = "-dev"
        }
        create("production") {
            dimension = "env"
        }
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }

    lint {
        abortOnError = true
        warningsAsErrors = true
        checkReleaseBuilds = true
        xmlReport = true
        htmlReport = true
        lintConfig = file("lint.xml")
    }

    sourceSets {
        // Adds exported schema location as test app assets.
        getByName("androidTest").assets.srcDir("$projectDir/schemas")
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.material.icons)

    // Hilt
    implementation(libs.dagger.hilt.android)
    kapt(libs.dagger.hilt.compiler)
    implementation(libs.hilt.navigation.compose)

    implementation(libs.lifecycle.viewmodel)
    implementation(libs.lifecycle.viewmodel.compose)
    implementation(libs.lifecycle.runtime.compose)

    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.moshi)
    //retrofit coroutines
    implementation(libs.retrofit.coroutine.adapter)
    //moshi
    implementation(libs.moshi)
    implementation(libs.moshi.kotlin)
    ksp(libs.moshi.kotlin.codegen)
    implementation(libs.kotlin.reflect)

    // Data store
    implementation(libs.datastore)
    implementation(libs.datastore.preferences)

    // Room
    implementation(libs.room.runtime)
    ksp(libs.room.compiler)
    implementation(libs.room.ktx)

    // Test & Debug
    testImplementation(libs.junit)
    testImplementation(libs.dagger.hilt.testing)
    // Fo mocking and fake instances/values
    testImplementation(libs.mokito)
    testImplementation(libs.mokito.kotlin)
    testImplementation(libs.mokito.inline)
    // Coroutine test rules
    testImplementation(libs.coroutine.testing)
    // Room
    testImplementation(libs.room.testing)
    androidTestImplementation(libs.dagger.hilt.testing)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    debugImplementation(libs.compose.ui.testing)
    // Needed for createAndroidComposeRule, but not createComposeRule:
    debugImplementation(libs.compose.testing.manifest)
    //kaptAndroidTest(libs.dagger.hilt.compiler)
    kaptAndroidTest(libs.dagger.hilt.android.compiler)
    kaptTest(libs.dagger.hilt.compiler)
}

//class RoomSchemaArgProvider(
//    @get:InputDirectory
//    @get:PathSensitive(PathSensitivity.RELATIVE)
//    val schemaDir: File
//) : CommandLineArgumentProvider {
//
//    override fun asArguments(): Iterable<String> {
//        return listOf("room.schemaLocation=${schemaDir.path}")
//    }
//}
//
//ksp {
//    arg(RoomSchemaArgProvider(File(projectDir, "schemas")))
//}