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
val appVersionDesktop = "1.0.0"


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
var appProfile = "PRODUCTION"
val appVersionCodeName = "SG3"

initGradleProperties()

kotlin {
    jvmToolchain(17)
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

            //implementation("org.jetbrains.androidx.navigation:navigation-compose:2.7.0-alpha07")
            implementation(libs.haze) //Haze's BlurView https://github.com/chrisbanes/haze
            implementation(libs.coil)
            implementation(libs.kotlinx.serialization.json)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.multiplatform.settings)
            implementation(libs.multiplatform.settings.no.arg)

            implementation(libs.okio)

            implementation(libs.coil.compose.core)

            //Ktor - Web Request I/O
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.cio)
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.client.serialization)

            // use api since the desktop app need to access the Cef to initialize it.
            //api(libs.compose.webview.multiplatform)

            // Precompose!
            api(libs.tlaster.precompose)

            //Kotlinx DateTime
            implementation(libs.kotlinx.datetime)

            //Sonner - Toast
            implementation(libs.sonner)

            implementation(libs.richeditor.compose)

            implementation(libs.compose.boxshadow)

            //implementation("androidx.annotation:annotation:1.8.2")

            //Screen Capture
            //implementation(libs.compose.multiplatform.screen.capture)

            //VerticalGrid
            implementation("com.cheonjaeung.compose.grid:grid:2.1.0")

            //Compose WebView Multiplatform : https://github.com/KevinnZou/compose-webview-multiplatform
            api("io.github.kevinnzou:compose-webview-multiplatform:1.9.40")

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
        applicationId = "com.voc.stargazer3"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = versionCodeFinal
        versionName = "1.0"
    }

    flavorDimensions += "version"
    productFlavors{
        properties["APP_PLATFORM"] = "Android"

        create("0dev"){
            appProfile = "DEV"
            versionName = "DEV ${appVersion} (${versionCodeFinal})"
        }
        create("beta"){
            appProfile = "BETA"
            versionName = "BETA ${appVersion} (${versionCodeFinal})"
        }
        create("closeBeta"){
            appProfile = "C.BETA"
            versionName = "C.BETA ${appVersion} (${versionCodeFinal})"
        }
        create("production_googleplay"){
            appProfile = "PRODUCTION_GP"
            versionName = "GP ${appVersion} (${versionCodeFinal})"
        }
        create("production"){
            appProfile = "PRODUCTION"
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
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb, TargetFormat.Exe)
            packageName = "Stargazer 3${if(appProfile.contains("PRODUCTION")) "" else " ($appProfile)"}"
            packageVersion = appVersionDesktop
            copyright = "Copyright © 2024 Coding Band 版權所有"
            description = "Stargazer 3 is an unofficial multiplatform app developed by Coding Band."
            vendor = "Coding Band"

            linux {
                iconFile.set(project.file("icon/app_icon.png"))
                shortcut = true
            }
            windows {
                iconFile.set(project.file("icon/app_icon.ico"))
                shortcut = true
                menu = true
                dirChooser = true
            }
            macOS{
                iconFile.set(project.file("icon/app_icon.icns"))

                //ref : https://github.com/JetBrains/compose-multiplatform/blob/master/tutorials/Signing_and_notarization_on_macOS/README.md#configuring-gradle
                bundleID = "com.voc.stargazer3"
                minimumSystemVersion = "12.0"
                signing {
                    appStore = true //https://youtrack.jetbrains.com/issue/CMP-4272
                    sign.set(true)
                    identity.set("Chun Man Tsang")
                }

                //provisioningProfile.set(project.file("stores/SG3_Mac_App_Provisioning_Profile.provisionprofile"))
                //runtimeProvisioningProfile.set(project.file("stores/JVM_Mac_App_Store_Provisioning_Profile.provisionprofile"))
            }
        }

        buildTypes.release.proguard {
            isEnabled = false
            version.set("7.5.0")
        }

        jvmArgs("--add-opens", "java.desktop/sun.awt=ALL-UNNAMED")
        jvmArgs(
            "--add-opens",
            "java.desktop/java.awt.peer=ALL-UNNAMED"
        ) // recommended but not necessary

        if (System.getProperty("os.name").contains("Mac")) {
            jvmArgs("--add-opens", "java.desktop/sun.lwawt=ALL-UNNAMED")
            jvmArgs("--add-opens", "java.desktop/sun.lwawt.macosx=ALL-UNNAMED")
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
