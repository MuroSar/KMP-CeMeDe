import java.util.Properties
import org.jetbrains.compose.ExperimentalComposeLibrary
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)

    // Ktor
    alias(libs.plugins.kotlin.serialization)

    // Room
    alias(libs.plugins.ksp)
    alias(libs.plugins.room)

    // Unit testing
    alias(libs.plugins.mockative)

    // ktlint
    alias(libs.plugins.ktlint)
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

kotlin {
    jvmToolchain(21)

    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_21)
        }
    }

    listOf(
        iosX64(),
        iosArm64(), // Dispositivo real
        iosSimulatorArm64(), // Simulador en Mac Apple Silicon
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }

    jvm {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_21)
        }
        compilations.getByName("main").defaultSourceSet.kotlin.srcDir("build/generated/version")
    }

    sourceSets {
        androidMain.dependencies {
            implementation(libs.compose.uiToolingPreview)
            implementation(libs.androidx.activity.compose)

            // DI
            implementation(libs.koin.android)
            implementation(libs.koin.androidx.compose)

            // Ktor and Coil
            implementation(libs.ktor.client.okhttp)
        }
        commonMain.dependencies {
            implementation(libs.compose.runtime)
            implementation(libs.compose.foundation)
            implementation(libs.compose.material3)
            implementation(libs.material.icons.core)
            implementation(libs.material.icons.extended)
            implementation(libs.compose.ui)
            implementation(libs.compose.components.resources)
            implementation(libs.compose.uiToolingPreview)

            // General
            implementation(libs.kotlinx.serialization.json)

            // ViewModel
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtime.compose)
            implementation(libs.androidx.lifecycle.viewmodel.compose)
            implementation(libs.androidx.lifecycle.viewmodel.savedstate)
            implementation(libs.kotlinx.coroutines.core)

            // Navigation
            implementation(libs.navigation.compose)

            // DI
            api(libs.koin.core)
            implementation(libs.koin.compose)
            implementation(libs.koin.compose.viewmodel)

            // Coil
            implementation(libs.coil.compose)
            implementation(libs.coil.network.ktor)

            // Ktor
            implementation(libs.bundles.ktor)

            // Room
            implementation(libs.room.runtime)
            implementation(libs.sqlite.bundled)

            // Compottie (Lottie KMP lib) --> https://github.com/alexzhirkevich/compottie/tree/standalone-main
            implementation(libs.compottie)

            // Clock
            implementation(libs.kotlinx.datetime)
        }
        commonTest.dependencies {
            // Unit testing
            implementation(libs.kotlin.test)
            implementation(kotlin("test-annotations-common"))
            implementation(libs.assertk)
            implementation(libs.mockative)
            implementation(libs.turbine)

            // UI testing
            @OptIn(ExperimentalComposeLibrary::class)
            implementation(compose.uiTest)
        }
        nativeMain.dependencies {
            // Ktor and Coil
            implementation(libs.ktor.client.darwin)
        }
        jvmMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutines.swing)

            // Ktor and Coil
            implementation(libs.ktor.client.okhttp)
        }
        // Room-2
        commonMain {
            kotlin.srcDir("build/generated/ksp/metadata")
        }
    }
    // Unit testing
    mockative {
        sourceSets {
            commonMain {
            }
        }
    }
}

android {
    // Lectura de local.properties para el firmado de la app
    val keystorePropertiesFile = rootProject.file("local.properties")
    val keystoreProperties = Properties()
    if (keystorePropertiesFile.exists()) {
        keystoreProperties.load(keystorePropertiesFile.inputStream())
    }

    namespace = "com.cemede.cemede"
    compileSdk =
        libs.versions.android.compileSdk
            .get()
            .toInt()

    signingConfigs {
        create("release") {
            keyAlias = keystoreProperties["release.keystore.alias"] as String?
            keyPassword = keystoreProperties["release.keystore.key.password"] as String?
            storeFile = keystoreProperties["release.keystore.path"]?.let { file(it) }
            storePassword = keystoreProperties["release.keystore.password"] as String?
        }
    }

    defaultConfig {
        applicationId = "com.cemede.cemede"
        minSdk =
            libs.versions.android.minSdk
                .get()
                .toInt()
        targetSdk =
            libs.versions.android.targetSdk
                .get()
                .toInt()
        versionCode = libs.versions.app.versionCode.get().toInt()
        versionName = libs.versions.app.versionName.get()
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            signingConfig = signingConfigs.getByName("release")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
}

dependencies {
    debugImplementation(compose.uiTooling)

    // Room
    ksp(libs.room.compiler)
    add("kspCommonMainMetadata", libs.room.compiler) // Esto genera el código compartido
//    add("kspAndroid", libs.room.compiler)
//    add("kspIosSimulatorArm64", libs.room.compiler)
//    add("kspIosX64", libs.room.compiler)
//    add("kspIosArm64", libs.room.compiler)
//    add("kspDesktop", libs.room.compiler)
}

compose.desktop {
    application {
        mainClass = "com.cemede.cemede.MainKt"

        jvmArgs += "-Dapp.version=${libs.versions.app.versionName.get()}"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "com.cemede.cemede"
            packageVersion = libs.versions.app.versionName.get()
        }
    }
}

// Tarea para sincronizar versiones con iOS
tasks.register("generateIosVersionConfig") {
    val versionFile = file("${project.rootDir}/iosApp/Configuration/Version.xcconfig")
    inputs.property("versionName", libs.versions.app.versionName.get())
    inputs.property("versionCode", libs.versions.app.versionCode.get())
    outputs.file(versionFile)

    doLast {
        versionFile.parentFile.mkdirs()
        versionFile.writeText(
            """
            // Generado automáticamente por Gradle - No editar manualmente
            MARKETING_VERSION_GRADLE = ${libs.versions.app.versionName.get()}
            CURRENT_PROJECT_VERSION_GRADLE = ${libs.versions.app.versionCode.get()}
            """.trimIndent(),
        )
    }
}

// Tarea para sincronizar versiones con Desktop
tasks.register("generateJvmVersionConfig") {
    val versionFile = file("$buildDir/generated/version/com/cemede/cemede/BuildInfo.kt")
    inputs.property("versionName", libs.versions.app.versionName.get())
    outputs.file(versionFile)

    doLast {
        versionFile.parentFile.mkdirs()
        versionFile.writeText(
            """
            package com.cemede.cemede

            internal object BuildInfo {
                const val VERSION = "${libs.versions.desktop.versionName.get()}"
            }
            """.trimIndent(),
        )
    }
}

// Asegurar que la tarea corra antes de compilar para iOS
tasks.matching { it.name.contains("embedAndSign") || it.name.contains("link") }.configureEach {
    dependsOn(tasks.named("generateIosVersionConfig"))
}

// Asegurar que la tarea corra antes de compilar para Desktop
tasks.matching { it.name == "compileKotlinJvm" || it.name == "jvmProcessResources" || it.name == "kspKotlinJvm" }.configureEach {
    dependsOn("generateJvmVersionConfig")
}

// Room
room {
    schemaDirectory("$projectDir/schemas")
}

ktlint {
    version.set("1.5.0") // ktlint (Pinterest) core version
    android.set(true) // Android-specific rules
    outputToConsole.set(true) // Shows errors in console
    ignoreFailures.set(false) // The build fails if issues are found

    reporters {
        reporter(org.jlleitschuh.gradle.ktlint.reporter.ReporterType.PLAIN)
        reporter(org.jlleitschuh.gradle.ktlint.reporter.ReporterType.CHECKSTYLE)
    }

    filter {
        exclude("**/build/**", "**/resources/**", "**/*.md", "**/*.properties", "**/generated/**")
    }

    // To execute ktlint you can run:
    // ./gradlew ktlintCheck
    // ./gradlew ktlintFormat
}
