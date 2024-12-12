import com.codingfeline.buildkonfig.compiler.FieldSpec.Type.INT
import com.codingfeline.buildkonfig.compiler.FieldSpec.Type.STRING
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework
import java.util.Properties

/**
 * VersionUpdateCheck
 * Environment Area - App Version
 */

val appVersion = "3.0.0"
val appVersionCodeName = "SG3"

val appVersionBeta = "2.9.9"
val appVersionCodeNameBeta = "Re:Stargazer"

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.compose.compiler)
    id("com.codingfeline.buildkonfig").version("0.15.1")
    kotlin("plugin.serialization") version "2.0.10"
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
var appProfile = "C.BETA"


kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }
    
    jvm("desktop")

    val xcf = XCFramework()

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "composeApp"
            isStatic = true
            binaryOption("bundleVersion", versionCodeFinal.toString())
            binaryOption("bundleShortVersionString", if(appProfile === "BETA" || appProfile === "C.BETA" || appProfile === "DEV") appVersionBeta else appVersion)
            xcf.add(this)
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
            //implementation("org.jetbrains.androidx.navigation:navigation-compose:2.7.0-alpha07")
            implementation(libs.haze) //Haze's BlurView https://github.com/chrisbanes/haze
            implementation(libs.coil)
            implementation(libs.kotlinx.serialization.json)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.multiplatform.settings)
            implementation(libs.multiplatform.settings.no.arg)

            implementation(libs.okio)

            implementation(libs.coil.network.ktor)
            implementation(libs.coil.compose.core)

            //Ktor - Web Request I/O
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.cio)
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.client.serialization)

            // use api since the desktop app need to access the Cef to initialize it.
            api(libs.compose.webview.multiplatform)

            // Precompose!
            api(libs.tlaster.precompose)

            //Kotlinx DateTime
            implementation(libs.kotlinx.datetime)

            //Sonner - Toast
            implementation(libs.sonner)

            implementation(libs.richeditor.compose)

            implementation(libs.compose.boxshadow)

            implementation("androidx.annotation:annotation:1.8.2")

            //Screen Capture
            //implementation(libs.compose.multiplatform.screen.capture)

        }
        desktopMain.dependencies {
            implementation(compose.material3)
            implementation(compose.desktop.currentOs) {
                exclude("org.jetbrains.compose.material")
            }
            // Explicitly include this is required to fix Proguard warnings coming from Kotlinx.DateTime
            implementation(libs.kotlinx.serialization.core)

        }
        nativeMain.dependencies {
            implementation(libs.ktor.client.darwin)
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
    bundle {
        language {
            enableSplit = false
        }
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