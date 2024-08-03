import com.codingfeline.buildkonfig.compiler.FieldSpec.Type.INT
import com.codingfeline.buildkonfig.compiler.FieldSpec.Type.STRING
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import java.util.Properties

/**
 * VersionUpdateCheck
 * Environment Area - App Version
 */

val appVersion = "2.3.2"
val appVersionCodeName = "Dan Heng"

val appVersionBeta = "2.4.0"
val appVersionCodeNameBeta = "Echo"

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.compose.compiler)
    id("com.codingfeline.buildkonfig").version("0.15.1")
    kotlin("plugin.serialization") version "2.0.0"
}

/**
 * tasks to gradle.properties
 */
val properties = Properties()
file("../gradle.properties").inputStream().use { properties.load(it) }

val versionCodeFinal = properties.getProperty("APP_VERSION_CODE").toInt() + 1
initGradleProperties()

//BETA | C.BETA | DEV | PRODUCTION
//VersionUpdateCheck
var appProfile = "DEV"


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
            implementation("com.mohamedrejeb.richeditor:richeditor-compose:1.0.0-rc05")

            implementation("com.squareup.okio:okio:3.9.0")

            implementation("io.coil-kt.coil3:coil:3.0.0-alpha08")
            implementation("io.coil-kt.coil3:coil-network-ktor:3.0.0-alpha08")
            implementation("io.coil-kt.coil3:coil-compose-core:3.0.0-alpha08")

            //Ktor - Web Request I/O
            implementation("io.ktor:ktor-client-core:2.0.0")
            implementation("io.ktor:ktor-client-cio:2.0.0")
            implementation("io.ktor:ktor-serialization-kotlinx-json:2.0.0")
            implementation("io.ktor:ktor-client-content-negotiation:2.0.0")
            implementation("io.ktor:ktor-client-serialization:2.0.0")

            // use api since the desktop app need to access the Cef to initialize it.
            api("io.github.kevinnzou:compose-webview-multiplatform:1.9.20")

            //Kotlinx DateTime
            implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.6.0")

        }
        desktopMain.dependencies {
            implementation(compose.material3)
            implementation(compose.desktop.currentOs) {
                exclude("org.jetbrains.compose.material")
            }
            // Explicitly include this is required to fix Proguard warnings coming from Kotlinx.DateTime
            implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:1.7.0")

        }
    }
}

android {
    namespace = "com.voc.honkaistargazer"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        applicationId = "com.voc.honkaistargazer"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = versionCodeFinal
        versionName = "1.0"
    }

    flavorDimensions += "version"
    productFlavors{
        properties["APP_PLATFORM"] = "Android"

        create("0dev"){
            applicationId = "com.voc.honkai_stargazer_gp"
            versionName = "DEV ${appVersionBeta} (${versionCodeFinal})"
        }
        create("beta"){
            applicationId = "com.voc.honkai_stargazer_beta"
            versionName = "BETA ${appVersionBeta} (${versionCodeFinal})"
        }
        create("closeBeta"){
            applicationId = "com.voc.honkai_stargazer_cbeta"
            versionName = "C.BETA ${appVersionBeta} (${versionCodeFinal})"
        }
        create("production"){
            applicationId = "com.voc.honkai_stargazer_gp"
            versionName = "${appVersion} (${versionCodeFinal})"
        }
        properties.store(file("../gradle.properties").outputStream(),null)
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
        implementation(libs.androidx.material3.android)
    }
}


compose.desktop {
    application {
        mainClass = "MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "com.voc.honkaistargazer"
            packageVersion = "1.0.0"
        }

        buildTypes.release.proguard {
            isEnabled = false
            version.set("7.5.0")
        }
    }
}

compose.resources {
    publicResClass = true
    packageOfResClass = "files"
    generateResClass = always
}


buildkonfig {
    packageName = "com.voc.honkaistargazer"
    //Read only
    defaultConfigs {
        buildConfigField(STRING, "appProfile", appProfile)
        buildConfigField(STRING, "appVersionName", (if(appProfile === "BETA" || appProfile === "C.BETA" || appProfile === "DEV") appVersionBeta else appVersion))
        buildConfigField(STRING, "appVersionCodeName", (if(appProfile === "BETA" || appProfile === "C.BETA" || appProfile === "DEV") appVersionCodeNameBeta else appVersionCodeName))
        buildConfigField(INT, "appVersionCode", properties.getProperty("APP_VERSION_CODE"))
    }
}


fun initGradleProperties(){
    //Write only
    properties["APP_VERSION"] = appVersion
    properties["APP_VERSION_BETA"] = appVersionBeta
    properties["APP_VERSION_CODENAME"] = appVersionCodeName
    properties["APP_VERSION_CODENAME_BETA"] = appVersionCodeNameBeta
    properties["APP_VERSION_CODE"] = versionCodeFinal.toString()
    properties.store(file("../gradle.properties").outputStream(),null)
}