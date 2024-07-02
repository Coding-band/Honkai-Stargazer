import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import java.util.Properties

buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        //classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.9.0")
        //classpath("com.codingfeline.buildkonfig:buildkonfig-gradle-plugin:latest_version")
    }
}

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.compose.compiler)
    kotlin("plugin.serialization") version "2.0.0"
    //id("com.codingfeline.buildkonfig")
}


kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }
    
    jvm("desktop")
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }

    applyDefaultHierarchyTemplate()


    sourceSets {
        val desktopMain by getting
        
        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)
        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation("org.jetbrains.androidx.navigation:navigation-compose:2.7.0-alpha07")
            implementation("dev.chrisbanes.haze:haze:0.6.2") //Haze's BlurView https://github.com/chrisbanes/haze
            implementation("io.coil-kt.coil3:coil:3.0.0-alpha06")
            implementation(libs.kotlinx.serialization.json)
            implementation(compose.components.uiToolingPreview)
            implementation("com.russhwolf:multiplatform-settings:1.1.1")
            implementation("com.russhwolf:multiplatform-settings-no-arg:1.1.1")
            //https://github.com/yshrsmz/BuildKonfig
            //implementation("com.codingfeline.buildkonfig:buildkonfig-gradle-plugin:0.15.1")


        }
        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)
        }
    }
}

android {
    namespace = "com.voc.honkaistargazer"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")


    val properties = Properties()
    file("../gradle.properties").inputStream().use { properties.load(it) }

    val versionProfile = properties.getProperty("APP_PROFILE").uppercase()
    val versionNameCommon = properties.getProperty(if (versionProfile === "RELEASE") "APP_VERSION" else "APP_VERSION_BETA")
    val versionCodeCommon = properties.getProperty("APP_VERSION_CODE").toInt() + 1

    properties["APP_VERSION_CODE"] = versionCodeCommon.toString()
    properties.store(file("../gradle.properties").outputStream(),null)

    defaultConfig {
        applicationId = "com.voc.honkaistargazer"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = versionCodeCommon
        versionName = "$versionProfile $versionNameCommon ($versionCodeCommon)"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        compose = true
    }
    dependencies {
        debugImplementation(compose.uiTooling)
    }
}
dependencies {
    implementation(libs.androidx.material3.android)
}


compose.desktop {
    application {
        mainClass = "MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "com.voc.honkaistargazer"
            packageVersion = "1.0.0"
        }
    }
}

compose.resources {
    publicResClass = true
    packageOfResClass = "files"
    generateResClass = always
}