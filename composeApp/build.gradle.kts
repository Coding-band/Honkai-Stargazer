import com.codingfeline.buildkonfig.compiler.FieldSpec.Type.INT
import com.codingfeline.buildkonfig.compiler.FieldSpec.Type.STRING
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Properties

/**
 * VersionUpdateCheck
 * Environment Area - App Version
 */


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

val appVersion: String = SimpleDateFormat("yyyy.MM.dd").format(Date())
val versionCodeFinal = properties.getProperty("APP_VERSION_CODE").toInt() + 1

//BETA | C.BETA | DEV | PRODUCTION
//VersionUpdateCheck
var appProfile = "C.BETA"
val appVersionCodeName = "SG3"

initGradleProperties()

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
            binaryOption("bundleShortVersionString", appVersion)
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
            //implementation(libs.lifecycle.viewmodel.compose)
            implementation(libs.adaptive)
            implementation(libs.coil.network.ktor)
            api("moe.tlaster:precompose-viewmodel:1.7.0-alpha01")

            //implementation("org.jetbrains.androidx.navigation:navigation-compose:2.7.0-alpha07")
            implementation(libs.haze) //Haze's BlurView https://github.com/chrisbanes/haze
            implementation(libs.coil)
            implementation(libs.kotlinx.serialization.json)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.multiplatform.settings)
            implementation(libs.multiplatform.settings.no.arg)

            implementation(libs.okio)

            implementation("io.coil-kt.coil3:coil-network-ktor3:3.0.0-rc01")
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

            //VerticalGrid
            implementation("com.cheonjaeung.compose.grid:grid:2.1.0")

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
            implementation("io.ktor:ktor-client-darwin:3.0.0")
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
            applicationId = "com.voc.stargazer3"
            versionName = "DEV ${appVersion} (${versionCodeFinal})"
        }
        create("beta"){
            applicationId = "com.voc.stargazer3_beta"
            versionName = "BETA ${appVersion} (${versionCodeFinal})"
        }
        create("closeBeta"){
            applicationId = "com.voc.stargazer3_cbeta"
            versionName = "C.BETA ${appVersion} (${versionCodeFinal})"
        }
        create("production_googleplay"){
            applicationId = "com.voc.stargazer3"
            appProfile = "PRODUCTION_GP"
            versionName = "GP ${appVersion} (${versionCodeFinal})"
        }
        create("production"){
            applicationId = "com.voc.stargazer3"
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
            packageName = "com.voc.stargazer3"
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
    packageName = "com.voc.stargazer3"
    //Read only
    defaultConfigs {
        buildConfigField(STRING, "appProfile", appProfile)
        buildConfigField(STRING, "appVersionName", appVersion)
        buildConfigField(STRING, "appVersionCodeName", appVersionCodeName)
        buildConfigField(INT, "appVersionCode", properties.getProperty("APP_VERSION_CODE"))
    }
}


fun initGradleProperties(){
    //Write only
    properties["APP_VERSION"] = appVersion
    properties["APP_VERSION_CODENAME"] = appVersionCodeName
    properties["APP_VERSION_CODE"] = versionCodeFinal.toString()
    properties.store(file("../gradle.properties").outputStream(),null)
}
